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
        private const val constraintLayoutVersion = "2.1.4"

        const val material = "com.google.android.material:material:$materialVersion"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:$constraintLayoutVersion"
    }

    object Tests {
        private const val junitVersion = "4.13.2"
        private const val junitExtVersion = "1.1.3"
        private const val testCoreVersion = "1.4.0"
        private const val hamcrestVersion = "1.3"
        private const val archTestingVersion = "2.1.0"
        private const val mockitoVersion = "3.11.1"
        private const val espressoVersion = "3.4.0"

        const val junit = "junit:junit:$junitVersion"
        const val junitExt = "androidx.test.ext:junit:$junitExtVersion"
        const val testCore = "androidx.test:core-ktx:$testCoreVersion"
        const val hamcrest = "org.hamcrest:hamcrest-all:$hamcrestVersion"
        const val archTesting = "androidx.arch.core:core-testing:$archTestingVersion"

        const val mockitoAndroid = "org.mockito:mockito-android:$mockitoVersion"

        const val espressoCore = "androidx.test.espresso:espresso-core:$espressoVersion"
        const val espressoContrib = "androidx.test.espresso:espresso-contrib:$espressoVersion"
    }

    object Fragment {
        private const val fragmentVersion = "1.4.1"

        const val fragment = "androidx.fragment:fragment-ktx:$fragmentVersion"
        const val fragmentTesting = "androidx.fragment:fragment-testing:$fragmentVersion"
    }

    object Lifecycle {
        private const val lifecycleVersion = "2.4.1"

        const val lifecycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleVersion"
        const val lifecycleLiveData = "androidx.lifecycle:lifecycle-livedata-ktx:$lifecycleVersion"
        const val lifecycleViewModel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion"
    }

    object Navigation {
        private const val navigationVersion = "2.4.2"

        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        const val navigationTesting = "androidx.navigation:navigation-testing:$navigationVersion"
    }

    object Room {
        private const val roomVersion = "2.4.2"

        const val roomRuntime = "androidx.room:room-runtime:$roomVersion"
        const val roomCompiler = "androidx.room:room-compiler:$roomVersion"
        const val roomRx = "androidx.room:room-rxjava3:$roomVersion"
    }

    object RxJava {
        private const val rxJavaVersion = "3.0.0"

        const val rxJava = "io.reactivex.rxjava3:rxjava:$rxJavaVersion"
        const val rxJavaAndroid = "io.reactivex.rxjava3:rxandroid:$rxJavaVersion"
    }

    object Dagger {
        private const val daggerVersion = "2.40.5"

        const val dagger = "com.google.dagger:dagger:$daggerVersion"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$daggerVersion"
    }

    object DataStore {
        private const val dataStoreVersion = "1.0.0"

        const val preferencesDataStore =
            "androidx.datastore:datastore-preferences:$dataStoreVersion"
        const val preferencesDataStoreRx =
            "androidx.datastore:datastore-preferences-rxjava3:$dataStoreVersion"
    }

    object Retrofit {
        private const val retrofitVersion = "2.9.0"

        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val moshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"
        const val retrofitRx = "com.squareup.retrofit2:adapter-rxjava3:$retrofitVersion"
    }

    object Glide {
        private const val glideVersion = "4.13.2"

        const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    }
}