import io.gitlab.arturbosch.detekt.Detekt
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.junit5) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.android) apply false
}

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()
    }
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
            this.jvmTarget = JavaVersion.VERSION_11.toString()
            reports {
                html.required.set(true)
            }
        }
    }
}