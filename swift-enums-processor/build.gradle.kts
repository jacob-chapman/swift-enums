import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform") version libs.versions.kotlin.get()
    id("maven-publish")
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.ksp)
                implementation(project(":swift-enums-core"))
            }
            kotlin.srcDir("src/main/kotlin")
            resources.srcDir("src/main/resources")
        }
    }
}

tasks.register("release") {
    dependsOn("publishJvmPublicationToGithubPackagesRepository")
    dependsOn("publishKotlinMultiplatformPublicationToGithubPackagesRepository")
}