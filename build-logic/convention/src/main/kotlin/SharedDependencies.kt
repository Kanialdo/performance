import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType

internal fun Project.shared() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("implementation", libs.findLibrary("androidx.core").get())

        add("implementation", libs.findLibrary("androidx.activity.compose").get())

        add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())
        add("implementation", libs.findLibrary("androidx.navigation.compose").get())

        add("testImplementation", libs.findLibrary("kotlinx.coroutines.test").get())
        add("testImplementation", libs.findLibrary("mockk.core").get())
        add("testImplementation", project(":core:testing"))

        add("androidTestImplementation", libs.findLibrary("mockk.android").get())
        add("androidTestImplementation", libs.findLibrary("androidx.test.ext").get())
        add("androidTestImplementation", libs.findLibrary("androidx.test.espresso.core").get())
    }
}

internal fun Project.commonDependencies() {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    dependencies {
        add("implementation", libs.findLibrary("kotlinx.datetime").get())
    }
}