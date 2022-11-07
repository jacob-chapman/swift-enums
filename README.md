# Swift Enums

This plugin gives the ability to generate Swift Enum classes and mappers to convert Kotlin Sealed classes. 
Converting to Swift Enum classes gives a better experience for native iOS developers.

### Getting Started

Add jitpack to your repositories.

````
allprojects {
    repositories {
        maven {
            url = URI("https://jitpack.io")
        }
    }
}
````

Next add the gradle plugin to your modules `build.gradle.kts` file.

````
plugins {
    id("io.jacobchapman.swiftenums") version "1.0.0"
}
````

Next add the annotation `@SwiftEnum` to any sealed class that you want a mapper made for. 
Then once the `compileKotlinIos*` task is run a file is generated in the `build/generated/ksp/ios*/ios*Main/resources/EnumMappers.swift`. 
This file can be added to your swift package or used in a ios project to map kotlin sealed classes. 

See `examples` module for an example of using the annotation, and `swiftenums-ios`