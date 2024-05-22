plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.composeComplier.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.hilt.gradlePlugin)
    implementation(libs.junit5.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.ksp.gradlePlugin)
    implementation(libs.room.gradlePlugin)

    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}