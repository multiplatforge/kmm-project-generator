@file:Suppress("PropertyName")

plugins {
    kotlin("multiplatform") apply false
    kotlin("native.cocoapods") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
}

apply("properties.gradle.kts")
val GRADLE_VERSION: String by extra

tasks {
    wrapper {
        gradleVersion = GRADLE_VERSION
    }
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}