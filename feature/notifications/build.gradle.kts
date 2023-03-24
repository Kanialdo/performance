plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.notifications"
}

dependencies {
    implementation(projects.core.domain)
}
