plugins {
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    add("implementation", libs.hilt.android)
    add("kapt", libs.hilt.compiler)
}