import io.gitlab.arturbosch.detekt.Detekt

plugins {
    `kotlin-dsl`
    id("performance.root")
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.junit5) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")

    detekt {
        ignoreFailures = false
        buildUponDefaultConfig = true
        parallel = true
        autoCorrect = false
        config.setFrom(files(project.rootDir.resolve("detekt.yml")))
        source = files(
            "src/main/java",
            "src/main/kotlin",
            "src/test/java",
            "src/test/kotlin",
            "build.gradle.kts",
            "settings.gradle.kts",
        )

        dependencies {
            detektPlugins(rootProject.libs.detekt.plugins.formatting)
        }
    }

    tasks {
        withType<Detekt> {
            this.jvmTarget = Versions.java.toString()
            reports {
                html.required.set(true)
            }
        }
    }
}