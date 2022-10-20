rootProject.name = "swift-enums"
pluginManagement {
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
include(
    ":swift-enums-core",
    ":swift-enums-processor",
    ":examples",
    ":swift-enums-plugin"
)