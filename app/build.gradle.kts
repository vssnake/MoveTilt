import com.uratxe.movetilt.Android
import com.uratxe.movetilt.AndroidX
import com.uratxe.movetilt.Firebase
import com.uratxe.movetilt.Libs
import com.uratxe.movetilt.Batura
import java.util.Properties
import java.io.FileInputStream

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

    flavorDimensions("app")

    productFlavors {
        create("anime") {
            dimension("app")
        }
        create("pokemon") {
            dimension("app")
        }
    }

    signingConfigs {
        create("release") {
            val keystorePropertiesFile = file("release-signing.properties")

            if (!keystorePropertiesFile.exists()) {
                logger.warn("Release builds may not work: signing config not found.")
                return@create
            }

            val keystoreProperties = Properties()
            keystoreProperties.load(FileInputStream(keystorePropertiesFile))

            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    apollo {
        setGenerateTransformedQueries(true)
        setGenerateKotlinModels(true)
    }

    buildFeatures {
        viewBinding = true
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

    sourceSets{
        this["main"].java.srcDir("src/main/kotlin")
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Libs.kotlinVersion}")
    implementation("androidx.appcompat:appcompat:${AndroidX.appCompat}")
    implementation("androidx.core:core-ktx:${AndroidX.coreKtx}")
    implementation("androidx.constraintlayout:constraintlayout:${Libs.constraintLayout}")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${Libs.lifecycle_version}")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-savedstate:${Libs.lifecycle_version}")
    implementation("androidx.lifecycle:lifecycle-common-java8:${Libs.lifecycle_version}")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${Libs.lifecycle_version}")

    implementation("androidx.navigation:navigation-fragment-ktx:${AndroidX.navigation}")
    implementation("androidx.navigation:navigation-ui-ktx:${AndroidX.navigation}")

    implementation("com.google.android.material:material:${Libs.material}")

    compileOnly("org.jetbrains:annotations:13.0")
    //Testing
    testImplementation("junit:junit:${Libs.junit}")
    androidTestImplementation("androidx.test.ext:junit:${Libs.androidJunit}")
    androidTestImplementation("androidx.test.espresso:espresso-core:${Libs.espresso}")

    //Android Library Modules
    implementation(project(mapOf("path" to ":mvvmi"))) //Uncomment when you test a project android library module
    //implementation("com.baturamobile.mvvmi:0.1") //Uncomment when you test local library with publishMavenLocal()
    //implementation("com.github.BaturaMobile.android-basics:commons:${Batura.mvvmi}") //Uncomment when you use Jitpack's library

    //Firebase
    implementation("com.google.firebase:firebase-core:${Firebase.core}")
    //Koin
    implementation("io.insert-koin:koin-core:${Libs.koin}")
    implementation("io.insert-koin:koin-android:${Libs.koin}")
    //GraphQL
    implementation("com.apollographql.apollo:apollo-runtime:${Libs.apollo}")
    implementation("com.apollographql.apollo:apollo-coroutines-support:${Libs.apollo}")
    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:${Libs.retrofit}")
    implementation("com.squareup.retrofit2:converter-moshi:${Libs.retrofit}")
    //JWT
    implementation("com.auth0.android:jwtdecode:${Libs.jwtdecode}")
    //Glide
    implementation( "com.github.bumptech.glide:glide:${Libs.glide}")
    annotationProcessor("com.github.bumptech.glide:compiler:${Libs.glide}")
    //Compose
    implementation ("androidx.ui:ui-tooling:0.1.0-dev02")
    implementation ("androidx.ui:ui-layout:0.1.0-dev02")
    implementation ("androidx.ui:ui-material:0.1.0-dev02")
}
