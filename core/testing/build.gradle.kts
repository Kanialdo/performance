plugins {
    id("performance.android.library")
}

android {
    namespace = "pl.krystiankaniowski.performance.testing"
}

dependencies {
    implementation(libs.junit5.jupiter.api)
    implementation(libs.junit5.jupiter.engine)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.mockk.core)
}