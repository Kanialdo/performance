
import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

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