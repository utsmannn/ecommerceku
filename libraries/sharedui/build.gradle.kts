plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
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
            baseName = "sharedui"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here

                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                api(compose.components.resources)

                api(libs.ktorClient.core)
                api(libs.ktorClient.contentNegotiation)
                api(libs.ktorClient.logging)
                api(libs.ktorClient.serialization)
                api(libs.kamelImage)

                implementation(projects.apis.product)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }

        val androidMain by getting {
            dependencies {
                api(libs.android.viewmodelKtx)
                api(libs.android.viewmodelCompose)
                api(libs.androidx.activity.compose)
            }
        }

        val iosMain by getting {
            dependencies {
                api(libs.ktorClient.darwin)
            }
        }
    }
}

android {
    namespace = "com.utsman.libraries.sharedui"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}
