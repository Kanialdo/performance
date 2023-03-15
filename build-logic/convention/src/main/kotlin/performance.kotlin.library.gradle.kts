plugins {
    kotlin("jvm")
    `java-library`
}

dependencies {
    commonDependencies()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}