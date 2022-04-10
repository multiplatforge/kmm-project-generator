import de.fayard.refreshVersions.core.versionFor

plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "orgpackages.myapplication.android"
    compileSdk = 32

    defaultConfig {
        applicationId = "orgpackages.myapplication"
        minSdk = 26
        targetSdk = 32
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = sourceCompatibility
    }
    kotlinOptions {
        jvmTarget = "${compileOptions.sourceCompatibility}"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.ui)
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":myapp-shared"))

    // Core
    implementation(AndroidX.core.ktx)

    // Lifecycle
    implementation(AndroidX.lifecycle.runtimeKtx)

    // Activity
    implementation(AndroidX.activity.compose)

    // Compose
    implementation(AndroidX.compose.ui)
    implementation(AndroidX.compose.ui.toolingPreview)

    // Material
    implementation("androidx.compose.material:material:${versionFor(AndroidX.compose.ui)}")
}