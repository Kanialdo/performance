
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.shared() {

    dependencies {
        add("implementation", libs.androidx.core)

        add("implementation", libs.androidx.activity.compose)

        add("implementation", libs.androidx.hilt.navigation.compose)
        add("implementation", libs.androidx.navigation.compose)

        add("testImplementation", libs.mockk.core)
        add("testImplementation", libs.turbine)
        add("testImplementation", project(":core:testing"))

        add("androidTestImplementation", libs.mockk.android)
        add("androidTestImplementation", libs.androidx.test.ext)
        add("androidTestImplementation", libs.androidx.test.espresso.core)
    }
}

internal fun Project.commonDependencies() {

    dependencies {
        add("implementation", libs.kotlinx.datetime)
        add("testImplementation", libs.kotlinx.coroutines.test)
    }
}