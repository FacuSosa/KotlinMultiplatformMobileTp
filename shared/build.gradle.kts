plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.6.10"
    id("app.cash.sqldelight") version "2.0.0"
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
        val sqlDelightVersion = "2.0.0"
        val napier = "2.6.1"

        val commonMain by getting {
            dependencies {
                //KTOR
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                //NAPIER
                implementation("io.github.aakira:napier:$napier")

                //SERIALIZATION
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")

                //SQLDelight
                implementation("app.cash.sqldelight:sqlite-driver:$sqlDelightVersion")


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
                implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")



            }
        }

        val iosMain by getting {
            dependencies {
                //KTOR
                implementation("io.ktor:ktor-client-ios:$ktorVersion")

                //SQLDelight
                implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")


            }
        }

    }
}

sqldelight{
    databases{
        create("AppDatabase"){
            packageName.set("com.pokedex.db")
        }
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
