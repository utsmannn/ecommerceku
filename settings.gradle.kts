rootProject.name = "eCommerceku"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":composeApp")
include(":libraries:core")
include(":features:home")
include(":apis:product")
include(":libraries:sharedui")
include(":features:detail")
include(":features:wishlist")
include(":apis:authentication")
include(":features:user")
include(":apis:cart")
include(":features:cart")
