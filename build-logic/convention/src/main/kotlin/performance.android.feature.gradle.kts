plugins {
    id("com.android.library")
    kotlin("android")
    id("performance.android.library.compose")
    id("performance.android.hilt")
}

android {
    configureKotlinAndroid()
}

dependencies {
    implementation(project(":core:ui"))

//    testImplementation(project(":core:testing"))
//    androidTestImplementation(project(":core:testing"))

    shared()
}
