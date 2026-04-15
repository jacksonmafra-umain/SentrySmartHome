plugins {
    alias(libs.plugins.sentry.android.library)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.umain.sentry.navigation"
}

dependencies {
    api(libs.kotlinx.serialization.json)
}
