plugins {
    id("performance.android.library")
}

android {
    namespace = "pl.krystiankaniowski.performance.model"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}