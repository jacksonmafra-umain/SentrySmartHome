plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

dependencies {
    compileOnly(libs.android.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.kotlin.compose.compiler.plugin)
    compileOnly(libs.ksp.gradle.plugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "sentry.android.application"
            implementationClass = "com.umain.sentry.convention.AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "sentry.android.library"
            implementationClass = "com.umain.sentry.convention.AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "sentry.android.feature"
            implementationClass = "com.umain.sentry.convention.AndroidFeatureConventionPlugin"
        }
        register("androidCompose") {
            id = "sentry.android.compose"
            implementationClass = "com.umain.sentry.convention.AndroidComposeConventionPlugin"
        }
        register("koin") {
            id = "sentry.koin"
            implementationClass = "com.umain.sentry.convention.KoinConventionPlugin"
        }
    }
}
