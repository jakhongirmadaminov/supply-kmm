enableFeaturePreview("VERSION_CATALOGS")
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
//        maven("https://maven.mozilla.org/maven2/")
//        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")

        maven("https://jitpack.io")
    }
}

rootProject.name = "Supply"
include(":androidApp")
include(":shared")