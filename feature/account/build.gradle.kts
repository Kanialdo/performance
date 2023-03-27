plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.account"
}

dependencies {
    implementation(projects.core.domain)
}
