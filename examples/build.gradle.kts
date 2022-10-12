import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform") version libs.versions.kotlin.get()
    id("com.google.devtools.ksp")
}

group = "com.jacobchapman"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
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
        val commonMain by getting {
            dependencies {
                implementation(project(":swift-enums-core"))
            }
        }
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

dependencies {
//    add("kspCommonMainMetadata", project(":test-processor"))
//    add("kspJvm", project(":test-processor"))
//    add("kspJvmTest", project(":test-processor"))
//    add("kspJs", project(":test-processor"))
//    add("kspJsTest", project(":test-processor"))
    configurations.filter { it.name.contains("ksp") }.forEach {
        println(it.name)
        add(it.name, project(":swift-enums-processor"))
    }
}