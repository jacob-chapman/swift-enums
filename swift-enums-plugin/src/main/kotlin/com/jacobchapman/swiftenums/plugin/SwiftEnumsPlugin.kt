package com.jacobchapman.swiftenums.plugin

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.Copy
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.project
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinCompile
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import javax.inject.Inject

open class SwiftEnumsExtension @Inject constructor(private val project: Project) {

    /**
     * Plugin helper to setup the copy task to move the swift enum mapper file to a better location
     * @param folderPath Path to the folder we want to move the files too
     * @param taskDependency name of task to have this move task run after
     */
    fun setFilePath(folderPath: String, taskDependency: String) {
        project.plugins.getPlugin(SwiftEnumsPlugin::class.java).setupFileCopy(folderPath, taskDependency, project)
    }
}

class SwiftEnumsPlugin : Plugin<Project> {

    private fun Project.kotlin(configure: Action<KotlinMultiplatformExtension>) =
        extensions.configure(KotlinMultiplatformExtension::class.java, configure)

    override fun apply(target: Project) {
        with(target) {
            plugins.apply("com.google.devtools.ksp")
            extensions.create("swiftEnums", SwiftEnumsExtension::class.java)
            kotlin {
                this.sourceSets.getByName("commonMain").dependencies {
                    implementation("io.jacobchapman:swift-enums:swift-enums-core:0.0.5")
                }
            }


            afterEvaluate {
                val dependentKspTasks = mutableListOf<String>()
                dependencies {
                    configurations.filter { it.name.contains("ksp") && it.name.contains("ios", true) }.forEach {
//                        println("[SwiftEnums] Adding Ksp: $it")
                        dependentKspTasks.add(it.name)
                        add(it.name, "io.jacobchapman:swift-enums:swift-enums-processor:0.0.5")
                    }
                }
                tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
                    if (name.contains("ios", true)) {
                        dependentKspTasks.forEach { dependsOn(it) }
                    }
                }
            }
        }
    }

    // todo
    fun setupFileCopy(folderPath: String, taskName: String, project: Project) {
        with(project) {
            try {
                val dependentTask = tasks.getByName(taskName)
                val moveTask = tasks.register<Copy>("moveSwiftEnumMapper", Copy::class.java) {
                    dependsOn(dependentTask)
                    mustRunAfter(dependentTask)
                    from(layout.buildDirectory.dir("$buildDir/generated/ksp/metadata/commonMain/resources"))
                    into(layout.buildDirectory.dir(folderPath))
                }
            } catch (t: Throwable) {
                println("[SwiftEnums] Failed to get dependent task")
                // NO OP
                return
            }
        }
    }
}