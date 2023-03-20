plugins {
    id("performance.android.library")
    id("performance.android.hilt")
}

android {
    defaultConfig {
        consumerProguardFiles("consumer-proguard-rules.pro")
    }
    namespace = "pl.krystiankaniowski.performance.datastore"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.datastore.preferences)
}
