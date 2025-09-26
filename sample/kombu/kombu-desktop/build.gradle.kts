import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.hotReload)
}

//kotlin {
//    jvm()
//
//    sourceSets {
//        commonMain.dependencies {
//            implementation(project(":sample:kombu:kombu-shared") )
//            implementation(compose.runtime)
//            implementation(compose.foundation)
//            implementation(compose.material3)
//            implementation(compose.ui)
//            implementation(compose.components.resources)
//            implementation(compose.components.uiToolingPreview)
//            implementation(libs.lifecycle.viewModel)
//            implementation(libs.lifecycle.runtimeCompose)
//        }
//        commonTest.dependencies {
//            implementation(libs.kotlin.test)
//        }
//        jvmMain.dependencies {
//            implementation(compose.desktop.currentOs)
//            implementation(libs.coroutines.swing)
//        }
//    }
//}

dependencies {
    implementation(project(":sample:kombu:kombu-shared"))
    implementation(compose.desktop.currentOs)
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
