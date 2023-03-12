plugins {
    id("performance.android.library")
}

android {
    namespace = "pl.krystiankaniowski.performance.domain"
}

dependencies {
    implementation(libs.kotlinx.datetime)
}