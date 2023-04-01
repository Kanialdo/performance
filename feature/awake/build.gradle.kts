plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.awake"
}

dependencies {
    implementation(projects.core.domain)
}
