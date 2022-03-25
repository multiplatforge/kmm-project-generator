buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20-M1")
        classpath("com.android.tools.build:gradle:7.3.0-alpha03")
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks {
    wrapper {
        gradleVersion = "7.4.1"
    }
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}