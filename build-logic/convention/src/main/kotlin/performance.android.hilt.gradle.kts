import dagger.hilt.android.plugin.HiltExtension

plugins {
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

extensions.configure<HiltExtension> {
    enableAggregatingTask = false
    enableExperimentalClasspathAggregation = true
}

dependencies {
    add("implementation", libs.hilt.android)
    ksp(libs.hilt.compiler)
}