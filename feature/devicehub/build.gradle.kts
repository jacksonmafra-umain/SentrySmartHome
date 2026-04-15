plugins {
    alias(libs.plugins.sentry.android.feature)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.umain.sentry.feature.devicehub"
}
