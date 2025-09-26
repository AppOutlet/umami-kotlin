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

    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitHooks)
    alias(libs.plugins.kover)
}

dependencies {
    dokka(project(":umami"))
    kover(project(":umami"))
}

dokka {
    moduleName.set("Umami Kotlin")

    dokkaPublications.html {
        outputDirectory.set(projectDir.resolve("docs/reference"))
    }

    pluginsConfiguration {
        html {
            val year = LocalDateTime.now().year
            footerMessage.set("© AppOutlet $year")
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
