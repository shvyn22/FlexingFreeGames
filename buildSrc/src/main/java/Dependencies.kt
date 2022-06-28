object Dependencies {

    object Kotlin {
        private const val kotlinVersion = "1.6.10"
        private const val ktxVersion = "1.8.0"

        const val kotlinStdlib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion"
        const val ktxCore = "androidx.core:core-ktx:$ktxVersion"
    }

    object AppCompat {
        private const val appCompatVersion = "1.4.2"

        const val appCompat = "androidx.appcompat:appcompat:$appCompatVersion"
    }

    object UI {
        private const val materialVersion = "1.6.1"

        const val material = "com.google.android.material:material:$materialVersion"
    }

    object Tests {
        private const val junitVersion = "4.13.2"
        private const val junitExtVersion = "1.1.3"
        private const val testCoreVersion = "1.4.0"
        private const val espressoVersion = "3.4.0"

        const val junit = "junit:junit:$junitVersion"
        const val junitExt = "androidx.test.ext:junit:$junitExtVersion"
        const val testCore = "androidx.test:core-ktx:$testCoreVersion"

        const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"
    }

    object Activity {
        private const val activityVersion = "1.4.1"

        const val activity = "androidx.activity:activity-ktx:$activityVersion"
        const val activityCompose = "androidx.activity:activity-compose:$activityVersion"
    }

    object Lifecycle {
        private const val lifecycleVersion = "2.4.1"

        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    }

    object Navigation {
        private const val navigationVersion = "2.5.0-rc01"

        const val navigationCompose = "androidx.navigation:navigation-compose:$navigationVersion"
    }

    object Room {
        private const val roomVersion = "2.4.2"

        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomKtx = "androidx.room:room-ktx:$roomVersion"
    }

    object Coroutines {
        private const val coroutinesVersion = "1.6.3"

        const val coroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object Hilt {
        private const val hiltVersion = "2.42"
        private const val hiltAndroidXVersion = "1.0.0-alpha03"
        private const val hiltComposeVersion = "1.0.0-rc01"

        const val hiltAndroid = "com.google.dagger:hilt-android:$hiltVersion"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:$hiltVersion"
        const val hiltAndroidCompiler = "androidx.hilt:hilt-compiler:$hiltAndroidXVersion"

        const val hiltNavigationCompose =
            "androidx.hilt:hilt-navigation-compose:$hiltComposeVersion"

        const val hiltTesting = "com.google.dagger:hilt-android-testing:$hiltVersion"
    }

    object DataStore {
        private const val dataStoreVersion = "1.0.0"

        const val preferencesDataStore =
            "androidx.datastore:datastore-preferences:$dataStoreVersion"
    }

    object Retrofit {
        private const val retrofitVersion = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
    }

    object Coil {
        private const val coilVersion = "1.4.0"

        const val coil = "io.coil-kt:coil-compose:$coilVersion"
    }

    object Compose {
        internal const val composeVersion = "1.1.1"
        private const val constraintComposeVersion = "1.1.0-alpha01"
        private const val accompanistVersion = "0.23.1"

        const val composeRuntime = "androidx.compose.runtime:runtime:$composeVersion"
        const val composeUI = "androidx.compose.ui:ui:$composeVersion"
        const val composeUITooling = "androidx.compose.ui:ui-tooling:$composeVersion"
        const val composeFoundation = "androidx.compose.foundation:foundation:$composeVersion"
        const val composeFoundationLayout =
            "androidx.compose.foundation:foundation-layout:$composeVersion"
        const val composeMaterial = "androidx.compose.material:material:$composeVersion"
        const val composeMaterialIcons =
            "androidx.compose.material:material-icons-extended:$composeVersion"
        const val composeAnimation = "androidx.compose.animation:animation:$composeVersion"

        const val composeConstraint =
            "androidx.constraintlayout:constraintlayout-compose:$constraintComposeVersion"
        const val composeAccompanistSystemUi =
            "com.google.accompanist:accompanist-systemuicontroller:$accompanistVersion"
        const val composeAccompanistPager =
            "com.google.accompanist:accompanist-pager:$accompanistVersion"

        const val composeUiTest = "androidx.compose.ui:ui-test-junit4:$composeVersion"
        const val composeTestManifest = "androidx.compose.ui:ui-test-manifest:$composeVersion"
    }
}