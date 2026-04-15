package com.umain.sentry.convention

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

/**
 * Wires up Koin 4 + Koin Annotations + the new Koin Compiler Plugin (1.0.0-RC1)
 * via KSP2. Modules that apply this plugin can use @Single, @KoinViewModel,
 * @Module and @ComponentScan — wiring is generated at compile time.
 *
 * Enables KOIN_CONFIG_CHECK so unresolved injections fail the build.
 */
class KoinConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.google.devtools.ksp")

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            add("implementation", libs.findLibrary("koin-core").get())
            add("implementation", libs.findLibrary("koin-android").get())
            add("implementation", libs.findLibrary("koin-compose").get())
            add("implementation", libs.findLibrary("koin-compose-viewmodel").get())
            add("implementation", libs.findLibrary("koin-annotations").get())
            add("ksp", libs.findLibrary("koin-ksp-compiler").get())
        }

        extensions.configure(com.google.devtools.ksp.gradle.KspExtension::class.java) {
            arg("KOIN_CONFIG_CHECK", "true")
            arg("KOIN_DEFAULT_MODULE", "false")
        }
    }
}
