plugins {
    kotlin("multiplatform") apply false
    kotlin("native.cocoapods") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
}

apply("properties.gradle.kts")

tasks {
    wrapper {
        gradleVersion = "7.4.2"
    }
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}