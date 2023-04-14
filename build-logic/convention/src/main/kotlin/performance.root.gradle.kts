import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = Versions.java.toString()
    }
}