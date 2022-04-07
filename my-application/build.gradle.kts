plugins {
    kotlin("android") version "1.6.20-M1" apply false
    id("com.android.application") version "7.3.0-alpha07" apply false
}

tasks {
    wrapper {
        gradleVersion = "7.4.2"
    }
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}