@file:Suppress("PropertyName")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
}

val ORG_IDENTIFIER: String by rootProject.extra
val SHARED_MODULE_VERSION: String by rootProject.extra
val TARGET_ANDROID_SDK_VERSION: Int by rootProject.extra
val MIN_ANDROID_SDK_VERSION: Int by rootProject.extra
val TARGET_IOS_VERSION: String by rootProject.extra
val JVM_BYTECODE_VERSION: JavaVersion by rootProject.extra

version = SHARED_MODULE_VERSION

kotlin {
    android()
    ios()

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidMain by getting
        val androidTest by getting

        val iosMain by getting
        val iosTest by getting
    }

    cocoapods {
        summary = "Code shared between the My Application Android & iOS apps"
        homepage = "https://example.com"
        ios.deploymentTarget = TARGET_IOS_VERSION
        podfile = project.file("../myapp-ios-app/Podfile")
        framework {
            baseName = "MyAppShared"
        }
    }
}

android {
    namespace = "$ORG_IDENTIFIER.shared"
    compileSdk = TARGET_ANDROID_SDK_VERSION
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = MIN_ANDROID_SDK_VERSION
        targetSdk = TARGET_ANDROID_SDK_VERSION
    }
    compileOptions {
        sourceCompatibility = JVM_BYTECODE_VERSION
        targetCompatibility = sourceCompatibility
    }
}

tasks {
    withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "$JVM_BYTECODE_VERSION"
        }
    }
}