plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.settings"
}

dependencies {
    implementation(projects.core.ui)
}
