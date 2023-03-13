plugins {
    id("performance.android.application")
    kotlin("kapt")
}

android {
    namespace = "pl.krystiankaniowski.performance"

    defaultConfig {
        applicationId = "pl.krystiankaniowski.performance"
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }

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

kapt {
    correctErrorTypes = true
}

dependencies {
    implementation(projects.core.database)
    implementation(projects.core.domain)
    implementation(projects.core.infrastructure)
    implementation(projects.core.model)
    implementation(projects.core.ui)
    implementation(projects.feature.timer)
    implementation(projects.feature.settings)
    implementation(projects.feature.stats)
}