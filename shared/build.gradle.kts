plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
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
            baseName = "shared"
        }
    }

    sourceSets {
        val ktorVersion = "2.0.0-beta-1"

        val commonMain by getting {
            dependencies {
                //KTOR
                implementation("io.ktor:ktor-client-core:$ktorVersion")

                //SERIALIZATION
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                //KTOR
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")

            }
        }

        val iosMain by getting {
            dependencies {
                //KTOR
                implementation("io.ktor:ktor-client-ios:$ktorVersion")

            }
        }

    }
}

android {
    namespace = "com.example.tppokedex"
    compileSdk = 33
    defaultConfig {
        minSdk = 26
    }
}