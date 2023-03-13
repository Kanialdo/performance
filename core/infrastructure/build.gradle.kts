plugins {
    id("performance.android.library")
    id("performance.android.hilt")
}

android {
    namespace = "pl.krystiankaniowski.performance.infrastructure"
}

dependencies {
    implementation(libs.kotlinx.datetime)
    implementation(projects.core.domain)
    implementation(projects.core.database)
    implementation(projects.core.model)
}