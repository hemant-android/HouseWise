plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.housewise"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.housewise"
        minSdk = 26
        //noinspection OldTargetApi
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
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

    // Place kotlinOptions here, inside the android block
   /* kotlinOptions {
        jvmTarget = "11"
    }
*/
    buildFeatures {
        compose = true
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

    implementation(libs.androidx.navigation.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material.icons.extended)

    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // ==========================================
    // ADD THESE NEW DEPENDENCIES BELOW
    // ==========================================

    // 1. Retrofit (For making API calls)
    implementation(libs.retrofit)

    // 2. Gson Converter (To parse JSON responses into Kotlin Data Classes)
    implementation(libs.converter.gson)

    // 3. OkHttp & Logging Interceptor (For intercepting tokens & debugging API calls in Logcat)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    // 4. ViewModel Compose (Required to use viewModel() inside Jetpack Compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}