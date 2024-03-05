import androidx.room.gradle.RoomExtension

plugins {
    id("performance.android.library")
    id("performance.android.hilt")
    id("performance.ksp")
    id("androidx.room")
}

android {
    namespace = "pl.krystiankaniowski.performance.database"
}

extensions.configure<RoomExtension> {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.domain)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    testImplementation(libs.androidx.room.testing)
}
