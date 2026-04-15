plugins {
    alias(libs.plugins.sentry.android.library)
    alias(libs.plugins.sentry.android.compose)
}

android {
    namespace = "com.umain.sentry.designsystem"
}

dependencies {
    api(project(":core:ui"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.compose.material.icons.extended)
}
