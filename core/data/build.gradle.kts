plugins {
    alias(libs.plugins.sentry.android.library)
    alias(libs.plugins.sentry.koin)
}

android {
    namespace = "com.umain.sentry.data"
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.kotlinx.coroutines.core)
}
