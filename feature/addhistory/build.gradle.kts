plugins {
    id("performance.android.feature")
}

android {
    namespace = "pl.krystiankaniowski.performance.adddetails"
}

dependencies {
    implementation(projects.core.domain)
    testImplementation(libs.turbine)
}
