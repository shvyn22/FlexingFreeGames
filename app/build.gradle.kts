plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled =  false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Config.composeVersion
    }
}

dependencies {
    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStdlib)
    implementation(Dependencies.Kotlin.ktxCore)

    // AppCompat
    implementation(Dependencies.AppCompat.appCompat)

    // Activity
    implementation(Dependencies.Activity.activity)

    // UI
    implementation(Dependencies.UI.material)

    // Tests
    androidTestImplementation(Dependencies.Tests.junit)
    androidTestImplementation(Dependencies.Tests.junitExt)
    androidTestImplementation(Dependencies.Tests.testCore)

    androidTestImplementation(Dependencies.Tests.espressoCore)

    androidTestImplementation(Dependencies.Hilt.hiltTesting)
    kaptAndroidTest(Dependencies.Hilt.hiltAndroidCompiler)

    androidTestImplementation(Dependencies.Compose.composeUiTest)
    debugImplementation(Dependencies.Compose.composeTestManifest)

    // Lifecycle
    implementation(Dependencies.Lifecycle.lifecycleRuntime)
    implementation(Dependencies.Lifecycle.lifecycleViewModel)

    // Room
    implementation(Dependencies.Room.roomRuntime)
    kapt(Dependencies.Room.roomCompiler)
    implementation(Dependencies.Room.roomKtx)

    // Coroutines
    implementation(Dependencies.Coroutines.coroutinesCore)
    implementation(Dependencies.Coroutines.coroutinesAndroid)

    // Dagger Hilt
    implementation(Dependencies.Hilt.hiltAndroid)
    kapt(Dependencies.Hilt.hiltCompiler)
    kapt(Dependencies.Hilt.hiltAndroidCompiler)

    // DataStore
    implementation(Dependencies.DataStore.preferencesDataStore)

    // Retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.moshiConverter)

    // Coil
    implementation(Dependencies.Coil.coil)

    // Compose
    implementation(Dependencies.Compose.composeRuntime)
    implementation(Dependencies.Compose.composeUI)
    implementation(Dependencies.Compose.composeUITooling)
    implementation(Dependencies.Compose.composeFoundation)
    implementation(Dependencies.Compose.composeFoundationLayout)
    implementation(Dependencies.Compose.composeMaterial)
    implementation(Dependencies.Compose.composeMaterialIcons)
    implementation(Dependencies.Compose.composeAnimation)
    implementation(Dependencies.Compose.composeConstraint)
    implementation(Dependencies.Compose.composeAccompanistSystemUi)
    implementation(Dependencies.Compose.composeAccompanistPager)
    implementation(Dependencies.Activity.activityCompose)
    implementation(Dependencies.Navigation.navigationCompose)
    implementation(Dependencies.Hilt.hiltNavigationCompose)
}