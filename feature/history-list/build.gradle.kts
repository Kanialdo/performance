plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.historylist"
}

dependencies {
    implementation(projects.core.ui)
}
