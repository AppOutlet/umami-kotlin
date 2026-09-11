import dev.nucleusframework.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.detekt)
    alias(libs.plugins.nucleus)
}

dependencies {
    implementation(projects.sample.kombu.shared)
    implementation(compose.desktop.currentOs)
    implementation(libs.coroutines.swing)
    implementation(libs.compose.ui.tooling.preview)
    detektPlugins(libs.detekt.formatting)
}

detekt {
    autoCorrect = true
    config.setFrom(file("$rootDir/detekt.yml"))
    buildUponDefaultConfig = true
    source.setFrom(
        "src/main/kotlin",
    )
}

nucleus.application {
    mainClass = "dev.appoutlet.kombu.MainKt"

    nativeDistributions {
        targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
        packageName = "dev.appoutlet.kombu"
        packageVersion = libs.versions.umami.get()

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
