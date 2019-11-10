import com.uratxe.movetilt.Libs

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath("com.android.tools.build:gradle:4.0.0-alpha02")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
        classpath ("com.google.gms:google-services:4.3.2")
        classpath ("com.google.firebase:firebase-appdistribution-gradle:1.1.0")
        classpath("com.apollographql.apollo:apollo-gradle-plugin:1.2.0")
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.1.0")


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven {
            url = uri("http://dl.bintray.com/apollographql/android")
        }
        
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}

