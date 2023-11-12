plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "authentication"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.multiplatformSettings.core)
                implementation(libs.multiplatformSettings.noArg)
                //put your multiplatform dependencies here
                api(projects.libraries.core)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

android {
    namespace = "com.utsman.api.authentication"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
