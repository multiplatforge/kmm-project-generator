pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "my-application"
include(
    ":myapp-android-app",
    ":myapp-shared",
)