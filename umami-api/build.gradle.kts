@file:OptIn(ExperimentalWasmDsl::class)

import java.time.LocalDateTime
import org.gradle.kotlin.dsl.dokkaPlugin
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kover)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.mokkery)
    alias(libs.plugins.serialization)
}

kotlin {
    jvmToolchain(21)
    androidTarget { publishLibraryVariants("release") }
    jvm()
    js { browser() }
    wasmJs { browser() }
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    macosX64()
    macosArm64()
    linuxX64()
    mingwX64()

    sourceSets {
        commonMain.dependencies {
            api(project(":umami"))
            implementation(libs.ktor.client.resources)
            implementation(libs.kotlinx.datetime)
        }

        commonTest.dependencies {
            implementation(libs.coroutines.test)
            implementation(libs.kotest.assertions)
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
            implementation(libs.ktor.serialization.json)
        }

        all {
            languageSettings.optIn("dev.appoutlet.umami.util.annotation.InternalUmamiApi")
            languageSettings.optIn("kotlin.time.ExperimentalTime")
        }
    }

    //https://kotlinlang.org/docs/native-objc-interop.html#export-of-kdoc-comments-to-generated-objective-c-headers
    targets.withType<org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget> {
        compilations["main"].compileTaskProvider.configure {
            compilerOptions {
                freeCompilerArgs.add("-Xexport-kdoc")
            }
        }
    }

}

dependencies {
    detektPlugins(libs.detekt.formatting)
    dokkaPlugin(libs.dokka.versioning)
}

detekt {
    autoCorrect = true
    config.setFrom(file("$rootDir/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    source.setFrom(
        "src/commonMain/kotlin",
        "src/commonTest/kotlin",
        "src/androidMain/kotlin",
        "src/iosMain/kotlin",
        "src/jsMain/kotlin",
        "src/jvmMain/kotlin",
        "src/linuxMain/kotlin",
        "src/macosMain/kotlin",
        "src/mingwMain/kotlin",
        "src/wasmJsMain/kotlin",
    )
}

android {
    namespace = "dev.appoutlet.umami.api"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}

val version = "0.3.2"

mavenPublishing {
    publishToMavenCentral(true)
    coordinates(groupId = "dev.appoutlet", artifactId = "umami-api", version = version)

    pom {
        name = "umami-api"
        description = "Umami API SDK for Kotlin projects"
        url = "https://github.com/AppOutlet/umami-kotlin"
        inceptionYear = "2025"

        licenses {
            license {
                name = "MIT"
                url = "https://opensource.org/licenses/MIT"
            }
        }

        developers {
            developer {
                id = "messiaslima"
                name = "Messias Junior"
                email = "messiaslima.03@gmail.com"
                url = "https://github.com/messiaslima"
            }
        }

        scm {
            url = "https://github.com/AppOutlet/umami-kotlin"
            connection = "scm:git:https://github.com/AppOutlet/umami-kotlin.git"
            developerConnection = "scm:git:ssh://github.com/AppOutlet/umami-kotlin.git"
        }
    }

    signAllPublications()
}

dokka {
    val currentVersion = version

    dokkaPublications.html {
        outputDirectory.set(projectDir.resolve("docs/reference/api"))
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
