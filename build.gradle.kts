import com.android.build.gradle.internal.tasks.factory.letIfPresent
import org.jetbrains.dokka.gradle.DokkaTaskPartial

plugins {
    alias(libs.plugins.multiplatform).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.maven.publish).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.detekt)
    alias(libs.plugins.dokka)
}

tasks.dokkaHtmlMultiModule {
    moduleName.set("Umami Kotlin")
    outputDirectory.set(layout.projectDirectory.dir("docs/reference"))
}

subprojects {
    tasks.withType<DokkaTaskPartial>().configureEach {
        dokkaSourceSets.configureEach {
            includes.from("module.md")
        }
    }
}

tasks.register("installGitHooks") {
    doLast {
        exec {
            commandLine("bash", "$rootDir/git-hooks/install-git-hooks.sh")
        }
    }
}

 afterEvaluate {
     val task = tasks.named("installGitHooks").get()
     task.actions.forEach { action ->
         action.execute(task)
     }
 }
