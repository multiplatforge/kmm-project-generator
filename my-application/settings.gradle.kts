pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

rootProject.name = "my-application"
include(
    ":myapp-android-app",
    ":myapp-shared",
)