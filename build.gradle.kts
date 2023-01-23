buildscript {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
  dependencies {
    classpath("dev.icerock.moko:resources-generator:0.20.1")
    classpath(libs.kotlin.pluginGradle)
    classpath(libs.android.pluginGradle)
  }
}

plugins {
  //trick: for the same plugin versions in all sub-modules
  id("com.android.library") version libs.versions.pluginGradle.get() apply false
  id("com.android.application") version libs.versions.pluginGradle.get() apply false
  kotlin("android") version "1.8.0" apply false
  kotlin("multiplatform")version libs.versions.kotlin.get() apply false
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}
