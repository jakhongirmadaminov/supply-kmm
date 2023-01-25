plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization") version "1.8.0"
  kotlin("native.cocoapods")
  id("com.android.library")
  id("dev.icerock.mobile.multiplatform-resources")
  id("dev.icerock.mobile.multiplatform-network-generator")
}
kotlin {
  val dependenciesList = listOf(
    "dev.icerock.moko:resources:0.20.1",
    "dev.icerock.moko:mvvm-core:0.15.0",
    "dev.icerock.moko:mvvm-flow-resources:0.15.0",
    "dev.icerock.moko:mvvm-core:0.15.0",
    "dev.icerock.moko:mvvm-flow:0.15.0"
  )
  android()
  // export correct artifact to use all classes of library directly from Swift
  targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
    binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
      dependenciesList.forEach {
        export(it)
      }
    }
  }


  cocoapods {
    summary = "Some description for the Shared Module"
    homepage = "Link to the Shared Module homepage"
    version = "1.0"
    ios.deploymentTarget = "14.1"
    podfile = project.file("../iosApp/Podfile")
    framework {
      baseName = "shared"
    }
  }
//  val xcf = XCFramework("MultiPlatformLibrary")
  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach { target ->
    target.binaries.framework {
      baseName = "MultiPlatformLibrary"
//      xcf.add(this)
//      dependenciesList.forEach { export(it) }
    }
  }


  sourceSets {
    val ktorVersion = "2.2.2"
    val coroutineVersion = "1.6.4"
    val commonMain by getting {
      dependencies {
        dependenciesList.forEach { api(it) }
        // Logger
        implementation("io.github.aakira:napier:2.6.1")
        // Ktor
        implementation("io.ktor:ktor-client-core:$ktorVersion")
        implementation("io.ktor:ktor-client-logging:$ktorVersion")
        implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
        // Coroutines
        api("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion")
        // Koin
        api(libs.io.koin.core)
        api("dev.icerock.moko:network:0.20.0")
        api("dev.icerock.moko:network-engine:0.20.0") // configured HttpClientEngine
        api("dev.icerock.moko:network-bignum:0.20.0") // kbignum serializer
        api("dev.icerock.moko:network-errors:0.20.0") // moko-errors integration
        api(libs.kotlin.serialization.json)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
        implementation("dev.icerock.moko:resources-test:0.20.1")
        implementation("dev.icerock.moko:mvvm-test:0.15.0")
      }
    }
    val androidMain by getting {
      dependencies {
        implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
        api("dev.icerock.moko:mvvm-flow-compose:0.15.0")
        api("dev.icerock.moko:resources-compose:0.20.1")
      }
    }
    val androidTest by getting
    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
      dependencies {
        implementation("io.ktor:ktor-client-darwin:$ktorVersion")
      }
    }
    val iosX64Test by getting
    val iosArm64Test by getting
    val iosSimulatorArm64Test by getting
    val iosTest by creating {
      dependsOn(commonTest)
      iosX64Test.dependsOn(this)
      iosArm64Test.dependsOn(this)
      iosSimulatorArm64Test.dependsOn(this)
    }
  }
}

mokoNetwork {
  spec("smartposSupply") {
    configureTask {
      skipValidateSpec.set(true)
    }
    inputSpec = file("src/api.json")
  }
//  spec("news") {
//    inputSpec = file("src/newsApi.yaml")
//    packageName = "news"
//    isInternal = false
//    isOpen = true
//    configureTask {
//      // here can be configuration of https://github.com/OpenAPITools/openapi-generator GenerateTask
//    }
//    enumFallbackNull = false
//  }
}

android {
  namespace = "uz.uzkass.smartpos.supply"
  compileSdk = libs.versions.compileSdk.get().toInt()
  defaultConfig {
    minSdk = libs.versions.minSdk.get().toInt()
    targetSdk = libs.versions.compileSdk.get().toInt()
  }

//    buildTypes {
//        debug {
//            buildConfigField("String", "BASE_URL", "\"https://api-devsupply.smartpos.uz/\"")
//        }
//    }

}
dependencies {
  implementation("androidx.core:core-ktx:1.9.0")
}

multiplatformResources {
  multiplatformResourcesPackage = "uz.uzkassa.smartpos.supply.library" // required
//  multiplatformResourcesClassName = "SharedRes" // optional, default MR
//  multiplatformResourcesVisibility = dev.icerock.gradle.MRVisibility.Internal // optional, default Public
  iosBaseLocalizationRegion = "ru" // optional, default "en"
//  multiplatformResourcesSourceSet = "commonClientMain"  // optional, default "commonMain"
  disableStaticFrameworkWarning = true

}

//
//afterEvaluate {
//  val xcodeDir = File(project.buildDir, "xcode")
//
//  tasks.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkTask>()
//    .forEach { xcFrameworkTask ->
//      val syncName: String = xcFrameworkTask.name.replace("assemble", "sync")
//      val xcframeworkDir =
//        File(xcFrameworkTask.outputDir, xcFrameworkTask.buildType.getName())
//
//      tasks.create(syncName, Sync::class) {
//        this.group = "xcode"
//
//        this.from(xcframeworkDir)
//        this.into(xcodeDir)
//
//        this.dependsOn(xcFrameworkTask)
//      }
//    }
//
//  tasks.filterIsInstance<org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFrameworkTask>()
//    .forEach { xcFrameworkTask ->
//      val frameworkDir: File = xcFrameworkTask.frameworks.first().outputFile
//      val swiftGenDir = File(frameworkDir.parent, frameworkDir.nameWithoutExtension + "Swift")
//      val xcframeworkDir =
//        File(xcFrameworkTask.outputDir, xcFrameworkTask.buildType.getName())
//      val targetDir = File(xcframeworkDir, swiftGenDir.name)
//
//      @Suppress("ObjectLiteralToLambda")
//      xcFrameworkTask.doLast(object : Action<Task> {
//        override fun execute(t: Task) {
//          targetDir.mkdirs()
//          swiftGenDir.copyRecursively(targetDir, overwrite = true)
//        }
//      })
//    }
//}
