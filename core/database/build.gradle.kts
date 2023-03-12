plugins {
    id("performance.android.library")
    id("performance.android.hilt")
    id("performance.ksp")
}

android {
    namespace = "pl.krystiankaniowski.performance.database"
}

dependencies {

    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.room.ktx)

    testImplementation(libs.androidx.room.testing)
}
