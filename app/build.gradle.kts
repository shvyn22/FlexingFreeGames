plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
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
        viewBinding = true
    }
    sourceSets {
        get("androidTest").java.srcDirs("src/androidTest/java", "src/sharedTest/java")
        get("test").java.srcDirs("src/test/java", "src/sharedTest/java")
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

    // Tests
    testImplementation(Dependencies.Tests.junit)
    testImplementation(Dependencies.Tests.testCore)
    testImplementation(Dependencies.Tests.turbine)
    testImplementation(Dependencies.Tests.hamcrest)
    testImplementation(Dependencies.Tests.archTesting)

    androidTestImplementation(Dependencies.Tests.junit)
    androidTestImplementation(Dependencies.Tests.junitExt)
    androidTestImplementation(Dependencies.Tests.testCore)
    androidTestImplementation(Dependencies.Tests.espressoCore)
    androidTestImplementation(Dependencies.Tests.espressoContrib)

    debugImplementation(Dependencies.Fragment.fragmentTesting)
    androidTestImplementation(Dependencies.Navigation.navigationTesting)
    testImplementation(Dependencies.Coroutines.coroutinesTesting)
    androidTestImplementation(Dependencies.Coroutines.coroutinesTesting)
    androidTestImplementation(Dependencies.Hilt.hiltTesting)
    kaptAndroidTest(Dependencies.Hilt.hiltAndroidCompiler)

    // Fragment
    implementation(Dependencies.Fragment.fragment)

    // Lifecycle
    implementation(Dependencies.Lifecycle.lifecycleRuntime)
    implementation(Dependencies.Lifecycle.lifecycleViewModel)

    // Navigation
    implementation(Dependencies.Navigation.navigationFragment)
    implementation(Dependencies.Navigation.navigationUi)

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

    // Glide
    implementation(Dependencies.Glide.glide)
}