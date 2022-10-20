import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform") version libs.versions.kotlin.get()
    id("maven-publish")
}

group = "com.jacobchapman.swiftenums"
version = libs.versions.swiftEnums.get()

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
    val xcFramework = XCFramework()
    ios() {
        binaries.framework {
            xcFramework.add(this)
        }
    }
    iosSimulatorArm64() {
        binaries.framework {
            xcFramework.add(this)
        }
    }

    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}
