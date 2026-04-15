package com.umain.sentry.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Applies the Koin Compiler Plugin 1.0.0-RC1 — a native Kotlin compiler
 * plugin (not KSP) — and the base Koin runtime dependencies.
 *
 * See:
 *   https://blog.insert-koin.io/unlocking-koin-compile-safety-6278840ab171
 *   https://insert-koin.io/docs/intro/koin-compiler-plugin/
 */
class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("io.insert-koin.compiler.plugin")

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            add("implementation", libs.findLibrary("koin-core").get())
            add("implementation", libs.findLibrary("koin-android").get())
            add("implementation", libs.findLibrary("koin-compose").get())
            add("implementation", libs.findLibrary("koin-compose-viewmodel").get())
            add("implementation", libs.findLibrary("koin-annotations").get())
        }
    }
}
