plugins {
    kotlin("jvm")
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.pluginPublish)
    id("maven-publish")
}


gradlePlugin {
    plugins {
        create("swiftEnums") {
            id = "io.jacobchapman.swiftenums"
            displayName = "Swift Enums"
            description = "This plugin gives the ability to generate Swift Enum classes and mappers to convert Kotlin Sealed classes."
            implementationClass = "com.jacobchapman.swiftenums.plugin.SwiftEnumsPlugin"
        }
    }
}

publishing {
    repositories {
        maven {
            name = "Jitpack"
            url = uri("https://jitpack.io")
        }
    }
}

pluginBundle {
    website = "https://github.com/jacob-chapman/"
    vcsUrl = "https://github.com/jacob-chapman/swift-enums"
    tags = listOf("kotlin", "swift", "ksp")
}



dependencies {
    implementation(libs.kspGradle)
    implementation(libs.kotlinGradle)
}

