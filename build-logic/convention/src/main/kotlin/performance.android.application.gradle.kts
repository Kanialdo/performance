plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    configureKotlinAndroid()
    configureCompose(this)
}