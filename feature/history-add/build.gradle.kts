plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.historyadd"
}

dependencies {
    implementation(projects.core.domain)
}
