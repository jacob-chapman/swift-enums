plugins {
    kotlin("jvm")
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.pluginPublish)
}

group="com.jacobchapman.swiftenums"
version = libs.versions.swiftEnums.get()


gradlePlugin {
    plugins {
        create("swiftEnums") {
            id = "com.jacobchapman.swiftenums"
            implementationClass = "com.jacobchapman.swiftenums.plugin.SwiftEnumsPlugin"
        }
    }
}

dependencies {
    implementation(libs.kspGradle)
    implementation(libs.kotlinGradle)
}

