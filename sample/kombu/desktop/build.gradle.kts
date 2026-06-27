import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

dependencies {
    implementation(projects.sample.kombu.shared)
    implementation(compose.desktop.currentOs)
    implementation(libs.coroutines.swing)
    implementation(libs.compose.ui.tooling.preview)
}

compose.desktop {
    application {
        mainClass = "dev.appoutlet.kombu.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "dev.appoutlet.kombu"
            packageVersion = "1.0.0"
        }
    }
}
