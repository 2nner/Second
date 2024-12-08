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

rootProject.name = "Second"
include(":app")
include(":core:model")
include(":core:network")
include(":core:data")
include(":core:designsystem")
include(":core:navigation")
include(":feature:home")
include(":feature:contract")
include(":feature:contract-detail")
include(":feature:contract-receiver")
