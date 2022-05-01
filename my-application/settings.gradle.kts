// Plugins —————————————————————————————————————————————————————————————————————————————————————————
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.40.2"
}

// refreshVersions —————————————————————————————————————————————————————————————————————————————————
refreshVersions {
    rejectVersionIf {
        candidate.stabilityLevel.isLessStableThan(current.stabilityLevel)
    }
}

// Dependencies ————————————————————————————————————————————————————————————————————————————————————
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

// Projects ————————————————————————————————————————————————————————————————————————————————————————
rootProject.name = "my-application"
include(
    ":myapp-shared",
    ":myapp-android-app",
)