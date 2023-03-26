plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.about"
}

dependencies {
    implementation(projects.core.domain)
}
