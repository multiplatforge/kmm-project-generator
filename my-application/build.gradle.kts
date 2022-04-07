plugins {
    kotlin("android") apply false
    id("com.android.application") apply false
}

tasks {
    wrapper {
        gradleVersion = "7.4.2"
    }
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}