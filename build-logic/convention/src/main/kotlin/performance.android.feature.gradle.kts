plugins {
    id("com.android.library")
    kotlin("android")
    id("performance.android.library.compose")
    id("performance.android.hilt")
    id("performance.junit5")
}

android {
    configureKotlinAndroid()
}

dependencies {
    implementation(project(":core:ui"))

    shared()
}