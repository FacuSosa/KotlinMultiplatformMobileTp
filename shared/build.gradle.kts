plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.10"
    id("com.squareup.sqldelight")
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
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                //NAPIER
                implementation("io.github.aakira:napier:2.6.1")

                //SERIALIZATION
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                //SQLDelight
                implementation("com.squareup.sqldelight:runtime:1.5.5")


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

                //SQLDelight
                implementation("com.squareup.sqldelight:android-driver:1.5.5")


            }
        }

        val iosMain by getting {
            dependencies {
                //KTOR
                implementation("io.ktor:ktor-client-ios:$ktorVersion")

                //SQLDelight
                implementation("com.squareup.sqldelight:native-driver:1.5.5")

            }
        }

    }
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.pokedex.db"
    }
}


android {
    namespace = "com.example.tppokedex"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}
dependencies {
    implementation("androidx.compose.ui:ui-text-android:1.5.1")
}
