import org.gradle.kotlin.dsl.ksp

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.project.newsapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.project.newsapp"
        minSdk = 24
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.work.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.core.splashscreen)

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")
    implementation("androidx.lifecycle:lifecycle-process:2.7.0")

    implementation("com.google.dagger:hilt-android:2.57.1")
    ksp("com.google.dagger:hilt-android-compiler:2.57.1")

    // Database
    implementation("io.realm.kotlin:library-base:1.13.0")

    val room_version = "2.8.4"
    implementation("androidx.room:room-runtime:$room_version")
    ksp("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:${room_version}")
    // optional - Test helpers
    testImplementation("androidx.room:room-testing:${room_version}")

    // optional - Paging 3 Integration
    implementation("androidx.room:room-paging:${room_version}")


    val paging_version = "3.4.0"

    implementation("androidx.paging:paging-runtime:$paging_version")

    implementation("com.squareup.retrofit2:retrofit:2.7.0")
    implementation("com.google.code.gson:gson:2.7.0")
    implementation("com.squareup.retrofit2:converter-gson:2.7.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // RealM DB
    implementation("io.realm.kotlin:library-base:1.13.0")
    val nav_version = "2.9.6"
    implementation("androidx.navigation:navigation-compose:$nav_version")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")

    implementation("io.coil-kt.coil3:coil-compose:3.0.0")
    implementation("io.coil-kt.coil3:coil-network-okhttp:3.0.0")


    val work_version = "2.11.1"
    val hilt_ext_version="1.2.0"
    implementation("androidx.hilt:hilt-work:$hilt_ext_version")
    ksp("androidx.hilt:hilt-compiler:$hilt_ext_version")
    // Kotlin + coroutines
    implementation("androidx.work:work-runtime-ktx:$work_version")
    implementation("androidx.constraintlayout:constraintlayout-compose:1.1.1")
    testImplementation("androidx.work:work-testing:2.11.1")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:34.9.0"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")

    // Dependency for the Firebase Authentication library
    implementation("com.google.firebase:firebase-auth")
    implementation("androidx.credentials:credentials:1.3.0")
    implementation("androidx.credentials:credentials-play-services-auth:1.3.0")
    implementation("com.google.android.libraries.identity.googleid:googleid:1.1.1")



}