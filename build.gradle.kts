// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    extra["kotlin_version"] = "1.4.10"
    repositories {
        google()
        mavenLocal()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.2.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath ("com.google.gms:google-services:4.3.4")
        classpath ("com.google.firebase:firebase-appdistribution-gradle:2.0.1")
        classpath("com.apollographql.apollo:apollo-gradle-plugin:1.2.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.3.2")
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:1.4.30")


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()

        mavenLocal() //Enabled it if when you publish the android library modules of your project
        // with "publishMavenLocal" Gradle's task in your laptop. It's an useful tool for test them.
        // We can find them in $HOME/.m2 folder. Remember set Gradle to "Offline Mode".

        maven("https://jcenter.bintray.com")
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}

