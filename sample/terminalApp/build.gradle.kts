import org.gradle.kotlin.dsl.project

plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(project(":umami-api"))
    implementation(libs.coroutines.swing)
}
