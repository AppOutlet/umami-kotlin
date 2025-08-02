import java.time.LocalDateTime

plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.maven.publish).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.gitHooks)
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

        versioning {
            renderVersionsNavigationOnAllPages.set(true)
            version.set("0.1.9")
        }
    }
}

dependencies {
    dokka(project(":umami"))
}