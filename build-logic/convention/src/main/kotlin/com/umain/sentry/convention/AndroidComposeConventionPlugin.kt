package com.umain.sentry.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Enables Jetpack Compose on an Android module. AGP 9 removed the
 * `composeOptions.kotlinCompilerExtensionVersion` DSL — the Compose compiler
 * is now provided by the `org.jetbrains.kotlin.plugin.compose` Gradle plugin,
 * which we apply here.
 */
class AndroidComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("org.jetbrains.kotlin.plugin.compose")

        val commonExtension: CommonExtension = when {
            pluginManager.hasPlugin("com.android.application") ->
                extensions.getByType(ApplicationExtension::class.java)
            pluginManager.hasPlugin("com.android.library") ->
                extensions.getByType(LibraryExtension::class.java)
            else -> error(
                "sentry.android.compose requires com.android.application or com.android.library",
            )
        }

        commonExtension.buildFeatures {
            compose = true
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            val bom = libs.findLibrary("compose-bom").get()
            add("implementation", platform(bom))
            add("androidTestImplementation", platform(bom))

            add("implementation", libs.findLibrary("compose-ui").get())
            add("implementation", libs.findLibrary("compose-ui-graphics").get())
            add("implementation", libs.findLibrary("compose-ui-tooling-preview").get())
            add("implementation", libs.findLibrary("compose-foundation").get())
            add("implementation", libs.findLibrary("compose-material3").get())
            add("debugImplementation", libs.findLibrary("compose-ui-tooling").get())
        }
    }
}
