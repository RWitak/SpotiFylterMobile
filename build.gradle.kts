// Top-level build file where you can add configuration options common to all subprojects/modules.
buildscript {
    dependencies {
        classpath("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
    }
}

plugins {
    id("com.android.application") version "7.3.0" apply false
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.7.21" apply false
}

tasks.register("clean") {
    delete(rootProject.buildDir)
}