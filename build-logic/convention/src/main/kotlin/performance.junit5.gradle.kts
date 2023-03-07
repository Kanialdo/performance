plugins {
    id("de.mannodermaus.android-junit5")
}

private val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("testImplementation", libs.findLibrary("junit5.jupiter.api").get())
    add("testRuntimeOnly", libs.findLibrary("junit5.jupiter.engine").get())
}