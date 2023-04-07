plugins {
    id("performance.android.library")
    id("performance.android.library.compose")
}

android {
    namespace = "pl.krystiankaniowski.performance.ui"
}

dependencies {
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.compose)
}