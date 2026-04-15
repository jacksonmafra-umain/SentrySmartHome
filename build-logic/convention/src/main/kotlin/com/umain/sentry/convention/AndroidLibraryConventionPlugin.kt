package com.umain.sentry.convention

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        // Kotlin Android is built into AGP 9 — applying the standalone plugin
        // now fails the build.
        pluginManager.apply("com.android.library")

        extensions.configure<LibraryExtension> {
            configureKotlinAndroid(this)
            // targetSdk is deprecated in AGP 9.x and is now inferred from compileSdk.
            // defaultConfig.targetSdk = libs.findVersion("targetSdk").get().toString().toInt()
        }
    }
}
