plugins {
    kotlin("jvm")
    `kotlin-dsl`
    `java-gradle-plugin`
    alias(libs.plugins.pluginPublish)
}


gradlePlugin {
    plugins {
        create("swiftEnums") {
            id = "io.jacobchapman.swiftenums"
            implementationClass = "com.jacobchapman.swiftenums.plugin.SwiftEnumsPlugin"
        }
    }
}

dependencies {
    implementation(libs.kspGradle)
    implementation(libs.kotlinGradle)
}

