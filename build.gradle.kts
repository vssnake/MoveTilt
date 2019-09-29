import com.uratxe.movetilt.Libs

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.6.0-alpha12")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
        classpath ("com.google.gms:google-services:4.3.2")
        classpath ("com.google.firebase:firebase-appdistribution-gradle:1.0.0")


        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        
    }
}

tasks.register("clean", Delete::class){
    delete(rootProject.buildDir)
}

