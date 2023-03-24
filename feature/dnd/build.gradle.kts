plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.dnd"
}

dependencies {
    implementation(projects.core.domain)
}
