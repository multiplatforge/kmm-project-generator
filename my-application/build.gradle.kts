@file:Suppress("PropertyName")

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

plugins {
    id("com.osacky.doctor")
    kotlin("multiplatform") apply false
    kotlin("native.cocoapods") apply false
    kotlin("android") apply false
    id("com.android.application") apply false
    id("com.android.library") apply false
}

apply("properties.gradle.kts")
val GRADLE_VERSION: String by extra

doctor {
    disallowCleanTaskDependencies.set(false)
}

subprojects {
    afterEvaluate {
        project.extensions.findByType<KotlinMultiplatformExtension>()?.apply {
            sourceSets.removeAll { sourceSet ->
                setOf(
                    "androidAndroidTestRelease",
                    "androidTestFixtures",
                    "androidTestFixturesDebug",
                    "androidTestFixturesRelease",
                ).contains(sourceSet.name)
            }
        }
    }
}

tasks {
    wrapper {
        gradleVersion = GRADLE_VERSION
    }
    register<Delete>("clean") {
        delete(rootProject.buildDir)
    }
}