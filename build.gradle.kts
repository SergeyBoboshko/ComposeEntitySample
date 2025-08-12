plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    //id("kotlin-kapt")
    //id("kotlin-parcelize")
    id("com.google.devtools.ksp") version "2.0.0-1.0.24"
}

android {
    namespace = "io.github.sergeyboboshko.composeentityksp_sample"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.sergeyboboshko.composeentityksp_sample"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"


    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }

    ksp {
        arg("skipTests", "true")
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation (project(":composeentity_ksp"))
    ksp(project(":composeentity_ksp"))
    implementation(project(":app"))

    implementation("androidx.navigation:navigation-compose:2.8.3")
    //val room_version = "2.6.1"
//    val room_version = "2.7.0-rc03"
//
//    implementation("androidx.room:room-runtime:$room_version")
//    implementation("androidx.room:room-ktx:$room_version")
//    kapt("androidx.room:room-compiler:$room_version")

    implementation ("androidx.webkit:webkit:1.4.0") //для інтеграції веб сторінок в компосе форми
    //runtimeOnly("com.google.accompanist:accompanist-webview:0.36.0")

}

//// Необходимо включить кодогенерацию для kapt
//kapt {
//    correctErrorTypes = true
//    useBuildCache = true
//    arguments {
//        arg("room.schemaLocation", "$projectDir/schemas")
//    }
//}

// Налаштування для KSP
ksp {
    arg("ksp.allow.all.target.configuration", "false")  // Це забезпечить застосування конфігурації лише для основного source set
}