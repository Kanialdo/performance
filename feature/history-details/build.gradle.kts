plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.historydetails"
}

dependencies {
    implementation(projects.core.domain)
}
