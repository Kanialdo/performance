plugins {
    id("com.android.application")
    kotlin("android")
    id("performance.android.hilt")
    id("performance.junit5")
}

android {
    configureKotlinAndroid()
    configureCompose(this)

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    commonDependencies()
    shared()
}