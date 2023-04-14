plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    commonDependencies()
    implementation(libs.kotlinx.coroutines.core)
}