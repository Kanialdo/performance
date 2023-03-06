import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("performance.android.feature")
    alias(libs.plugins.junit5)
}

android {
    namespace = "pl.krystiankaniowski.performance.timer"

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
    implementation(projects.core.ui)

    testImplementation(libs.junit5.jupiter.api)
    testImplementation(libs.kotlinx.coroutines.test)
    testRuntimeOnly(libs.junit5.jupiter.engine)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
}