plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    configureCompose(this)
}

val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    implementation(platform(libs.findLibrary("androidx.compose.bom").get()))
    implementation(libs.findLibrary("androidx.compose.ui").get())
    implementation(libs.findLibrary("androidx.compose.ui.graphics").get())
    implementation(libs.findLibrary("androidx.compose.ui.tooling.preview").get())
    implementation(libs.findLibrary("androidx.compose.material3").get())
    androidTestImplementation(platform(libs.findLibrary("androidx.compose.bom").get()))
    androidTestImplementation(libs.findLibrary("androidx.compose.ui.test.junit4").get())
    debugImplementation(libs.findLibrary("androidx.compose.ui.tooling").get())
    debugImplementation(libs.findLibrary("androidx.compose.ui.testManifest").get())
}