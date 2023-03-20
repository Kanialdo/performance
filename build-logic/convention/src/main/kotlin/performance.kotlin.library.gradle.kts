plugins {
    kotlin("jvm")
    `java-library`
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    commonDependencies()
    implementation(libs.findLibrary("kotlinx.coroutines.core").get())
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}