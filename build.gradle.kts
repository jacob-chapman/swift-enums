import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.net.URI

plugins {
    kotlin("multiplatform") version libs.versions.kotlin.get() apply false
    id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
    id("maven-publish")
}
buildscript {
    val githubProperties = java.util.Properties()
    try {
        githubProperties.load(java.io.FileInputStream("github.properties"))
    } catch (ex: Exception) {
    }
    repositories {
        maven {
            name = "GithubPackages"
            url = uri("https://maven.pkg.github.com/jacob-chapman/swift-enums")
            credentials {
                username = project.findProperty("githubUser") as? String ?: "jacob-chapman"
                password = githubProperties["gpr.token"] as? String ?: project.findProperty("githubToken") as? String
            }
        }
    }
}


allprojects {
    plugins.apply("maven-publish")
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            url = URI("https://jitpack.io")
        }
    }

    publishing {
        repositories {
            val githubProperties = java.util.Properties()
            try {
                githubProperties.load(java.io.FileInputStream("github.properties"))
            } catch (ex: Exception) {
            }
            repositories {
                maven {
                    name = "GithubPackages"
                    url = uri("https://maven.pkg.github.com/jacob-chapman/swift-enums")
                    credentials {
                        username = project.findProperty("githubUser") as? String ?: "jacob-chapman"
                        password = githubProperties["gpr.token"] as? String ?: project.findProperty("githubToken") as? String
                    }
                }
            }
        }
    }
}