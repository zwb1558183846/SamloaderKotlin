plugins {
    id("org.jetbrains.compose") version "0.3.0"
    id("com.android.application")
    kotlin("android")
}

group = "tk.zwander"
version = "1.0"

repositories {
    google()
}

dependencies {
    implementation(project(":common"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.4.31")
    implementation("androidx.compose.ui:ui:1.0.0-beta01")
    implementation("androidx.compose.ui:ui-tooling:1.0.0-beta01")
    implementation("androidx.compose.foundation:foundation:1.0.0-beta01")
    implementation("androidx.compose.material:material:1.0.0-beta01")
    implementation("androidx.activity:activity-compose:1.3.0-alpha03")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        applicationId = "tk.zwander.samsungfirmwaredownloader"
        minSdkVersion(24)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_1_8)
        targetCompatibility(JavaVersion.VERSION_1_8)
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

//    composeOptions {
//        kotlinCompilerExtensionVersion = ("1.0.0-beta01")
//    }
}

tasks.register<Wrapper>("wrapper") {
    gradleVersion = "6.7.1"
}
