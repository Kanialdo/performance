plugins {
    id("performance.android.library")
    id("performance.android.library.compose")
    alias(libs.plugins.junit5)
}

android {
    namespace = "pl.krystiankaniowski.performance.ui"

    buildTypes {
        debug {
        }
        named("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
}

dependencies {
    testImplementation(libs.junit5.jupiter.api)
    testRuntimeOnly(libs.junit5.jupiter.engine)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}