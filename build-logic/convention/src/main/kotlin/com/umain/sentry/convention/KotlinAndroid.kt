package com.umain.sentry.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

/**
 * Shared Android configuration applied by both the application and library
 * convention plugins.
 *
 * AGP 9 dropped:
 *  - the six-parameter generic on [CommonExtension]
 *  - the `Action<T>` block-DSL forms (`defaultConfig { ... }`, `compileOptions { ... }`, …)
 *
 * So we set everything via direct property access, which is the surviving
 * API on AGP 9.
 */
internal fun Project.configureKotlinAndroid(
    commonExtension: CommonExtension,
) {
    commonExtension.compileSdk =
        libs.findVersion("compileSdk").get().toString().toInt()

    commonExtension.defaultConfig.minSdk =
        libs.findVersion("minSdk").get().toString().toInt()

    commonExtension.compileOptions.sourceCompatibility = JavaVersion.VERSION_21
    commonExtension.compileOptions.targetCompatibility = JavaVersion.VERSION_21

    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_21
            freeCompilerArgs.addAll(
                "-Xcontext-parameters",
                "-opt-in=kotlin.RequiresOptIn",
            )
        }
    }
}
