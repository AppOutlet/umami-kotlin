import kotlinx.kover.gradle.aggregation.settings.dsl.minBound
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit

rootProject.name = "umami-kotlin"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("android")
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("android")
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":umami")
include(":umami-api")
include(":sample:simple-compose-app:composeApp")
include(":sample:terminalApp")
include(":sample:kombu:androidApp")
include(":sample:kombu:desktopApp")
include(":sample:kombu:shared")
include(":sample:kombu:webApp")


plugins {
    id("org.jetbrains.kotlinx.kover.aggregation") version "0.9.8"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

kover {
    skipProjects(":sample:simple-compose-app:composeApp", ":sample:terminalApp")

    reports {
        verify {
            rule {
                minBound(80, CoverageUnit.LINE, AggregationType.COVERED_PERCENTAGE)
                minBound(80, CoverageUnit.INSTRUCTION, AggregationType.COVERED_PERCENTAGE)
                minBound(36, CoverageUnit.BRANCH, AggregationType.COVERED_PERCENTAGE)
            }
        }
    }
}
