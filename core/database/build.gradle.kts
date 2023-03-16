plugins {
    id("performance.android.library")
    id("performance.android.hilt")
    id("performance.android.room")
}

android {
    namespace = "pl.krystiankaniowski.performance.database"
}

dependencies {

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    testImplementation(libs.androidx.room.testing)
}
