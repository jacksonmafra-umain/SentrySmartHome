package com.umain.sentry.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // AGP 9 bundles Kotlin Android support — applying the standalone
        // `org.jetbrains.kotlin.android` plugin would now raise
        // AgpWithBuiltInKotlinAppliedCheck. See kotl.in/gradle/agp-built-in-kotlin.
        pluginManager.apply("com.android.application")

        extensions.configure<ApplicationExtension> {
            configureKotlinAndroid(this)
            defaultConfig.targetSdk =
                libs.findVersion("targetSdk").get().toString().toInt()
        }
    }
}
