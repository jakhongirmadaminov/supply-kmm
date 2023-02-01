plugins {
    id("com.android.application")
    kotlin("android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "uz.uzkass.smartpos.supply.android"
    compileSdk = libs.versions.compileSdk.get().toInt()
    defaultConfig {
        applicationId = "uz.uzkass.smartpos.supply.android"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.compileSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    kotlin {
        sourceSets {
            debug.configure {
                kotlin.srcDir("build/generated/ksp/debug/kotlin")
            }
            release.configure {
                kotlin.srcDir("build/generated/ksp/release/kotlin")
            }
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:+")
    val bom = libs.androidx.compose.bom.get()

    implementation(platform(bom))
    androidTestImplementation(platform(bom))
//    implementation("androidx.compose.ui:ui-tooling:1.3.3")
//    implementation("androidx.compose.foundation:foundation:1.3.1")
//    implementation("androidx.compose.material:material:1.3.1")
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material.material)
    implementation("androidx.activity:activity-compose:1.6.1")

    implementation(libs.raamcosta.compose.destinations.core)
    implementation(libs.raamcosta.compose.destinations.animations)
    ksp(libs.raamcosta.compose.destinations.ksp)
    implementation(libs.coil)
//  Koin
    implementation(libs.io.koin.android)
    implementation(libs.io.koin.compose)
}