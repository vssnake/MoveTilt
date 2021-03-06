

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("maven-publish")
    id("xyz.tynn.android.sources") version("0.1.2")
    id ("org.jetbrains.dokka")
}


android {
    compileSdkVersion(30)
    buildToolsVersion = "30.0.3"
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 2
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}


dependencies {
    //implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.72")
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.core:core-ktx:1.3.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta7")

    testImplementation("junit:junit:4.13")
    androidTestImplementation("androidx.test.ext:junit:1.1.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    implementation("com.google.firebase:firebase-core:17.4.3")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-common-java8:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")

    implementation("androidx.navigation:navigation-fragment-ktx:2.2.2")
    implementation("androidx.navigation:navigation-ui-ktx:2.2.2")

    implementation("com.google.android.material:material:1.1.0")

    implementation("com.github.bumptech.glide:glide:4.11.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.11.0")



    implementation ("androidx.ui:ui-tooling:0.1.0-dev04")
    implementation ("androidx.ui:ui-layout:0.1.0-dev04")
    implementation ("androidx.ui:ui-material:0.1.0-dev04")

    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.7.1")
    implementation ("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.0.0")

    implementation ("joda-time:joda-time:2.10.4")

    implementation("com.google.android.libraries.maps:maps:3.1.0-beta")
    implementation("com.google.android.gms:play-services-maps:17.0.0")
}

// Because the components are created only during the afterEvaluate phase, you must
// configure your publications using the afterEvaluate() lifecycle method.
afterEvaluate {
    publishing {
        publications {
            // Creates a Maven publication called "release".
            create<MavenPublication>("release"){
                groupId = "com.baturamobile"
                artifactId = "commons"
                version = "0.1"

                from(components["release"])
            }
        }
    }

}