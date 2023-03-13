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
    implementation(project(":core:domain"))
    implementation(project(":core:model"))

    commonDependencies()
    shared()
}
