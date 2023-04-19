plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.timer"
}

dependencies {
    implementation(projects.core.ui)
}
