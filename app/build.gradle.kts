plugins {
    alias(libs.plugins.sentry.android.application)
    alias(libs.plugins.sentry.android.compose)
    alias(libs.plugins.sentry.koin)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.umain.sentry"

    defaultConfig {
        applicationId = "com.umain.sentry"
        versionCode = 1
        versionName = "0.1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:ui"))
    implementation(project(":core:data"))
    implementation(project(":core:navigation"))
    implementation(project(":feature:home"))
    implementation(project(":feature:room"))
    implementation(project(":feature:activities"))
    implementation(project(":feature:devicehub"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)
}
