import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.net.URI

plugins {
    kotlin("multiplatform") version libs.versions.kotlin.get() apply false
    id("com.google.devtools.ksp") version libs.versions.ksp.get() apply false
}

allprojects {
    repositories {
        google()
        mavenCentral()
        mavenLocal()
        maven {
            url = URI("https://jitpack.io")
        }
    }
}