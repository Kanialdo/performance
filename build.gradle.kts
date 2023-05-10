plugins {
    `kotlin-dsl`
    id("performance.root")
}

kotlin {
    sourceSets.all {
        languageSettings {
            languageVersion = "2.0"
        }
    }
}