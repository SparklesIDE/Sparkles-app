pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

rootProject.name = "Sparkles-App"

include(":app")
include(":peekandpop")
include(":maskable")
include(":ui-utils")
include(":filetree")
include(":java-compiler")
include(":fabrevealmenu")