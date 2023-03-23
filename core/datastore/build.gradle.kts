plugins {
    id("performance.android.library")
    id("performance.android.hilt")
}

android {
    namespace = "pl.krystiankaniowski.performance.datastore"
}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.androidx.datastore.preferences)
}
