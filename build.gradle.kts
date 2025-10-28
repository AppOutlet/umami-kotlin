import java.time.LocalDateTime
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.compose.hotReload) apply false
    alias(libs.plugins.compose.multiplatform) apply false
    alias(libs.plugins.maven.publish) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.jvm) apply false

    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitHooks)
    alias(libs.plugins.kover)
    alias(libs.plugins.kotlin.android) apply false
}

dependencies {
    dokka(project(":umami"))
    kover(project(":umami"))
    dokkaPlugin(libs.dokka.versioning)
}

val version = "0.2.1"

dokka {
    val currentVersion = version

    dokkaPublications.html {
        outputDirectory.set(projectDir.resolve("docs/reference"))
    }

    dokkaSourceSets {
        configureEach {
            includes.from(project.layout.projectDirectory.file("module.md"))
        }
    }

    pluginsConfiguration {
        html {
            val year = LocalDateTime.now().year
            footerMessage.set("© AppOutlet $year")
        }

        versioning {
            version.set(currentVersion)
            olderVersionsDir.set(projectDir.resolve("docs/versions"))
            renderVersionsNavigationOnAllPages.set(true)
        }
    }
}

kover {
    reports {
        verify {
            rule {
                minBound(
                    minValue = 77,
                    coverageUnits = CoverageUnit.LINE,
                    aggregationForGroup = AggregationType.COVERED_PERCENTAGE
                )

                minBound(
                    minValue = 78,
                    coverageUnits = CoverageUnit.INSTRUCTION,
                    aggregationForGroup = AggregationType.COVERED_PERCENTAGE
                )

                minBound(
                    minValue = 25,
                    coverageUnits = CoverageUnit.BRANCH,
                    aggregationForGroup = AggregationType.COVERED_PERCENTAGE,
                )
            }
        }
    }
}
