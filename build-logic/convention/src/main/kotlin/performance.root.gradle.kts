import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
    apply(plugin = "performance.detekt")

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Versions.java.toString()
    }
}