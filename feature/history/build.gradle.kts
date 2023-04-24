plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.history"
}

dependencies {
    implementation(projects.core.ui)
}
