plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.stats"
}

dependencies {
    implementation(projects.core.domain)
}
