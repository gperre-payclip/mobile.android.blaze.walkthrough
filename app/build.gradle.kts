plugins {
    alias(clipLibs.plugins.android.application)
    alias(clipLibs.plugins.kotlin.android)
    alias(clipLibs.plugins.kotlin.serialization)
    alias(clipLibs.plugins.hilt)
    id("kotlin-kapt")
}

android {
    namespace = "com.payclip.blaze.spike.walkthrough"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.payclip.blaze.spike.walkthrough"
        minSdk = 24
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
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }

    kotlinOptions {
        jvmTarget = clipLibs.versions.jvmTarget.get()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = clipLibs.versions.androidxComposeCompiler.get()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(clipLibs.androidx.coreKtx)
    implementation(clipLibs.androidx.lifecycle.runtime)
    implementation(clipLibs.kotlin.serialization)

    //Compose
    implementation(platform(clipLibs.androidx.compose.bom))
    implementation(clipLibs.androidx.compose.runtime.livedata)
    implementation(clipLibs.androidx.compose.material3)
    implementation(clipLibs.androidx.compose.material3.windowSize)
    implementation(clipLibs.androidx.compose.ui)
    implementation(clipLibs.androidx.compose.ui.graphics)
    implementation(clipLibs.androidx.compose.ui.tooling)
    implementation(clipLibs.androidx.compose.ui.toolingPreview)
    implementation(clipLibs.androidx.compose.navigation)
    implementation(clipLibs.androidx.compose.constraintLayout)
    implementation(clipLibs.androidx.lifecycle.runtimeCompose)
    implementation(clipLibs.androidx.lifecycle.viewModelCompose)
    implementation(clipLibs.androidx.activity.compose)
    implementation(clipLibs.androidx.splashScreen)

    //Hilt
    implementation(clipLibs.hilt.android)
    implementation(clipLibs.androidx.hilt.navigationCompose)
    kapt(clipLibs.hilt.compiler)

    //Retrofit
    implementation(clipLibs.retrofitCore)
    implementation(clipLibs.retrofit.converterGson)
    implementation(clipLibs.gsonCore)
    implementation(clipLibs.okhttpCore)
    implementation(clipLibs.okhttpLogging)

    //Test
    testImplementation(clipLibs.junit4)
    testImplementation(clipLibs.mockito.kotlin)
    testImplementation(clipLibs.mockk)
    testImplementation(clipLibs.coroutines.test)
    androidTestImplementation(clipLibs.androidx.test.ext)
    androidTestImplementation(clipLibs.androidx.test.espressoCore)
}

kapt {
    correctErrorTypes = true
}
