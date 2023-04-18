plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.vibrations"
}

dependencies {
    implementation(projects.core.domain)
}
