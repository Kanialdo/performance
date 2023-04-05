plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    commonDependencies()
    implementation(libs.kotlinx.coroutines.core)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}