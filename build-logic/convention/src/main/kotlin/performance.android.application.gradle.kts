plugins {
    id("com.android.application")
    kotlin("android")
    id("performance.android.hilt")
}

android {
    configureKotlinAndroid()
    configureCompose(this)
}

dependencies {
    shared()
}