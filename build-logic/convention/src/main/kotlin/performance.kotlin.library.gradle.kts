plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    testImplementation(kotlin("test"))

    commonDependencies()
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.junit5.jupiter.api)
    testRuntimeOnly(libs.junit5.jupiter.engine)
    testImplementation(libs.junit5.jupiter.params)
}

tasks.test {
    useJUnitPlatform()
}