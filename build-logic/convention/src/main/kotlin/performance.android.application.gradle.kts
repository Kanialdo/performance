plugins {
    id("com.android.application")
    kotlin("android")
    id("performance.android.hilt")
    id("performance.junit5")
    id("org.jetbrains.kotlin.plugin.compose")
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