import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.maven.publish)
    alias(libs.plugins.serialization)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
}
kotlin {
    jvmToolchain(17)

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
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.serialization)
            implementation(libs.ktor.serialization.json)
            implementation(libs.ktor.client.logging)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        macosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

        linuxMain.dependencies {
            implementation(libs.ktor.client.curl)
        }

        mingwMain.dependencies {
            implementation(libs.ktor.client.winhttp)
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
    namespace = "dev.appoutlet"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }
}

//Publishing your Kotlin Multiplatform library to Maven Central
//https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-publish-libraries.html
mavenPublishing {
    publishToMavenCentral(host = SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
    coordinates(groupId = "dev.appoutlet", artifactId = "umami", version = "0.1.5")

    pom {
        name = "umami"
        description = "Umami SDK for Kotlin projects"
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