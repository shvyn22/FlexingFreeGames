plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    compileSdk = Config.compileSdk

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdk
        targetSdk = Config.targetSdk
        versionCode = Config.versionCode
        versionName = Config.versionName
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
        viewBinding = true
    }
}

dependencies {
    // Kotlin
    implementation(Dependencies.Kotlin.kotlinStdlib)
    implementation(Dependencies.Kotlin.ktxCore)

    // AppCompat
    implementation(Dependencies.AppCompat.appCompat)

    // UI
    implementation(Dependencies.UI.material)
    implementation(Dependencies.UI.constraintLayout)

    // Fragment
    implementation(Dependencies.Fragment.fragment)

    // Lifecycle
    implementation(Dependencies.Lifecycle.lifecycleRuntime)
    implementation(Dependencies.Lifecycle.lifecycleLiveData)
    implementation(Dependencies.Lifecycle.lifecycleViewModel)

    // Navigation
    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)

    // Room
    implementation(Dependencies.Room.roomRuntime)
    kapt(Dependencies.Room.roomCompiler)
    implementation(Dependencies.Room.roomRx)

    // RxJava
    implementation(Dependencies.RxJava.rxJava)
    implementation(Dependencies.RxJava.rxJavaAndroid)

    // Dagger
    implementation(Dependencies.Dagger.dagger)
    kapt(Dependencies.Dagger.daggerCompiler)

    // DataStore
    implementation(Dependencies.DataStore.preferencesDataStore)
    implementation(Dependencies.DataStore.preferencesDataStoreRx)

    // Retrofit
    implementation(Dependencies.Retrofit.retrofit)
    implementation(Dependencies.Retrofit.moshiConverter)
    implementation(Dependencies.Retrofit.retrofitRx)

    // Glide
    implementation(Dependencies.Glide.glide)
}