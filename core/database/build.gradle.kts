plugins {
    id("performance.android.library")
    id("performance.android.hilt")
    id("performance.ksp")
}

android {
    namespace = "pl.krystiankaniowski.performance.database"
}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
    arg("room.incremental", "true")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.domain)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    testImplementation(libs.androidx.room.testing)
}
