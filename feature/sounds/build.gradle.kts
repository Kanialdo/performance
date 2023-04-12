plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.sound"
}

dependencies {
    implementation(projects.core.domain)
}
