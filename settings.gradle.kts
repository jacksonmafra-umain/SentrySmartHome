pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "SentrySmartHome"

include(":app")
include(":core:designsystem")
include(":core:ui")
include(":core:data")
include(":core:navigation")
include(":feature:home")
include(":feature:room")
include(":feature:activities")
include(":feature:devicehub")
