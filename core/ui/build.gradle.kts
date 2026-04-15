plugins {
    alias(libs.plugins.sentry.android.library)
    alias(libs.plugins.sentry.android.compose)
}

android {
    namespace = "com.umain.sentry.ui"
}

dependencies {
    implementation(libs.androidx.core.ktx)
}
