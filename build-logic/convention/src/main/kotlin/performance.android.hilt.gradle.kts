plugins {
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    add("implementation", libs.findLibrary("hilt.android").get())
    add("kapt", libs.findLibrary("hilt.compiler").get())
}