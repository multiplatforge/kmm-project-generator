plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    compileSdk = 32
    defaultConfig {
        applicationId = "orgpackages.myapplication.android"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":myapp-shared"))
    implementation(Google.android.material)
    implementation(AndroidX.appCompat)
    implementation(AndroidX.constraintLayout)
}