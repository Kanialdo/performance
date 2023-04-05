plugins {
    id("de.mannodermaus.android-junit5")
}

dependencies {
    add("testImplementation", libs.junit5.jupiter.api)
    add("testRuntimeOnly", libs.junit5.jupiter.engine)
}