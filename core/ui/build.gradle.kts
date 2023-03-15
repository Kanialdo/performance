plugins {
    id("performance.android.library")
    id("performance.android.library.compose")
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
    implementation(libs.androidx.core)
    implementation(libs.androidx.activity.compose)
}