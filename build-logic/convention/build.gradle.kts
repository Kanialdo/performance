plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.hilt.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.junit5.gradlePlugin)
}