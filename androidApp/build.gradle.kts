plugins {
    id("com.android.application")
    kotlin("android")
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
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.core:core-ktx:+")
    val bom = libs.androidx.compose.bom.get()

    implementation( platform(bom))
    androidTestImplementation( platform(bom))
//    implementation("androidx.compose.ui:ui-tooling:1.3.3")
//    implementation("androidx.compose.foundation:foundation:1.3.1")
//    implementation("androidx.compose.material:material:1.3.1")
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material.material)
    implementation("androidx.activity:activity-compose:1.6.1")
}