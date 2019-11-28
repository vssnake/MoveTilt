import com.uratxe.movetilt.Android
import com.uratxe.movetilt.AndroidX
import com.uratxe.movetilt.Firebase
import com.uratxe.movetilt.Libs

plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    kotlin("android")
    kotlin("android.extensions")
    id("kotlin-android-extensions")
    id("com.apollographql.android")
    id("androidx.navigation.safeargs.kotlin")
}


android {
    compileSdkVersion(Android.compiledSdk)
    buildToolsVersion = Android.buildToolsVersion
    defaultConfig {
        applicationId = "com.uratxe.movetilt"
        minSdkVersion(Android.minSdk)
        targetSdkVersion(Android.targetSdk)
        versionCode = Android.versionCode
        versionName = Android.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = File("uratxeSign.jks")
            storePassword = "topotaRaven69"
            keyAlias = "miRatu"
            keyPassword = "Iri_okupa"
        }
    }

    apollo {
        setGenerateTransformedQueries(true)
        setGenerateKotlinModels(true)
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            firebaseAppDistribution {
                releaseNotes = "Hola que haze + firma Pro"
                testers = "virtual.solid.snake@gmail.com, iratxels25@gmail.com"
                serviceCredentialsFile = "/Users/vssnake/Documents/movetilt-appdistrbution.json"
            }
            signingConfig = signingConfigs.getByName("release")
        }
    }


    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}


dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Libs.kotlinVersion}")
    implementation("androidx.appcompat:appcompat:${AndroidX.appCompat}")
    implementation("androidx.core:core-ktx:${AndroidX.coreKtx}")
    implementation("androidx.constraintlayout:constraintlayout:${Libs.constraintLayout}")

    testImplementation("junit:junit:${Libs.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Libs.androidJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Libs.espresso}")

    implementation("com.google.firebase:firebase-core:${Firebase.core}")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Libs.lifecycle_version}")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Libs.lifecycle_version}")
    // For Kotlin use lifecycle-viewmodel-ktx

    implementation("androidx.lifecycle:lifecycle-common-java8:${Libs.lifecycle_version}")

    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Libs.lifecycle_version}")

    implementation("org.koin:koin-android-viewmodel:${Libs.koin}")
    implementation("org.koin:koin-android-scope:${Libs.koin}")

    implementation("com.auth0.android:jwtdecode:${Libs.jwtdecode}")

    implementation("com.apollographql.apollo:apollo-runtime:${Libs.apollo}")
    implementation("com.apollographql.apollo:apollo-coroutines-support:${Libs.apollo}")

    implementation("androidx.navigation:navigation-fragment-ktx:${AndroidX.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${AndroidX.navigation}")

    implementation("com.google.android.material:material:${Libs.material}")

    implementation( "com.github.bumptech.glide:glide:${Libs.glide}")
    annotationProcessor("com.github.bumptech.glide:compiler:${Libs.glide}")

    compileOnly("org.jetbrains:annotations:13.0")


}
