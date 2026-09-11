import dev.nucleusframework.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.nucleus)
}

detekt {
    autoCorrect = true
    config.setFrom(file("$rootDir/detekt.yml"))
    buildUponDefaultConfig = true
    source.setFrom(
        "src/main/kotlin",
    )
}

kotlin.jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(25))
    vendor.set(JvmVendorSpec.JETBRAINS)
}

nucleus.application {
    mainClass = "dev.appoutlet.kombu.MainKt"

    nativeDistributions {
        packageName = "dev.appoutlet.kombu"
        packageVersion = libs.versions.umami.get()
        enableAotCache = true

        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)

        macOS {
            iconFile.set(file("$projectDir/icon/icon.icns"))
        }

        linux {
            iconFile.set(file("$projectDir/icon/icon.png"))
        }

        windows {
            iconFile.set(file("$projectDir/icon/icon.ico"))
        }
    }
}

dependencies {
    implementation(projects.sample.kombu.shared)

    implementation(compose.desktop.currentOs)

    implementation(libs.coroutines.swing)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.nucleus.application)
    implementation(libs.nucleus.decoratedWindow.tao)

    detektPlugins(libs.detekt.formatting)
}
