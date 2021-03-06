object Versions {
    const val kotlinVersion = "1.3.72"

    const val androidxAppCompatVersion = "1.1.0"
    const val androidxCoreVersion = "1.3.0"
    const val androidxLifecycleVersion = "2.2.0"
    const val androidxFragmentKtxVersion = "1.2.5"
    const val constraintLayoutVersion = "1.1.3"
    const val androidMaterialVersion = "1.1.0"
    const val androidxNavigationVersion = "2.3.0"
    const val architectureTestingVersion = "1.1.1"
    const val koinVersion = "2.1.6"
    const val rxKotlinVersion = "2.4.0"
    const val rxAndroidVersion = "2.1.1"
    const val retrofitVersion = "2.9.0"
    const val okHttpVersion = "4.8.0"
    const val moshiVersion = "1.9.3"
    const val glideVersion = "4.11.0"
    const val timberVersion = "4.7.1"
    const val mockitoVersion = "3.4.4"
    const val junitVersion = "4.13"
    const val androidxTestRunnerVersion = "1.1.1"
    const val androidxEspressoVersion = "3.2.0"
    const val lottieVersion = "3.4.1"
    const val androidYoutubePlayerVersion = "10.0.5"
    const val nhaarmanMockitoKotlinVersion = "2.2.0"
    const val firebaseCrashlyticsVersion = "17.1.0"
    const val firebaseAnalyticsVersion = "17.4.4"
}

object Libs {
    val kotlin = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    val androidxAppCompat = "androidx.appcompat:appcompat:${Versions.androidxAppCompatVersion}"
    val androidxCore = "androidx.core:core-ktx:${Versions.androidxCoreVersion}"
    val androidxLifecycle = "androidx.lifecycle:lifecycle-extensions:${Versions.androidxLifecycleVersion}"
    val androidxLifecyleKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.androidxLifecycleVersion}"
    val androidxFragmentKtx = "androidx.fragment:fragment-ktx:${Versions.androidxFragmentKtxVersion}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayoutVersion}"
    val androidMaterial = "com.google.android.material:material:${Versions.androidMaterialVersion}"
    val androidxNaviagtionFragment = "androidx.navigation:navigation-fragment:${Versions.androidxNavigationVersion}"
    val androidxNaviagtionUI = "androidx.navigation:navigation-ui:${Versions.androidxNavigationVersion}"
    val androidxNaviagtionFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.androidxNavigationVersion}"
    val androidxNaviagtionUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.androidxNavigationVersion}"

    val koin = "org.koin:koin-core:${Versions.koinVersion}"
    val koinExt = "org.koin:koin-core-ext:${Versions.koinVersion}"
    val koinAndroid = "org.koin:koin-android:${Versions.koinVersion}"
    val koinScope = "org.koin:koin-androidx-scope:${Versions.koinVersion}"
    val koinViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koinVersion}"
    val koinTest = "org.koin:koin-test:${Versions.koinVersion}"

    val rxKotlin = "io.reactivex.rxjava2:rxkotlin:${Versions.rxKotlinVersion}"
    val rxAndroid = "io.reactivex.rxjava2:rxandroid:${Versions.rxAndroidVersion}"

    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofitVersion}"
    val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:${Versions.retrofitVersion}"
    val retrofitRxJavaAdapter = "com.squareup.retrofit2:adapter-rxjava2:${Versions.retrofitVersion}"
    val okHttp3 = "com.squareup.okhttp3:okhttp:${Versions.okHttpVersion}"
    val okHttp3LoggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttpVersion}"
    val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Versions.moshiVersion}"
    val moshiKotlinCodeGen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshiVersion}"
    val moshiAdapters = "com.squareup.moshi:moshi-adapters:${Versions.moshiVersion}"

    val glide = "com.github.bumptech.glide:glide:${Versions.glideVersion}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glideVersion}"
    val timber = "com.jakewharton.timber:timber:${Versions.timberVersion}"
    val lottie = "com.airbnb.android:lottie:${Versions.lottieVersion}"
    val youtubePlayer = "com.pierfrancescosoffritti.androidyoutubeplayer:core:${Versions.androidYoutubePlayerVersion}"

    val firebaseCrashlytics = "com.google.firebase:firebase-crashlytics:${Versions.firebaseCrashlyticsVersion}"
    val firebaseAnalytics = "com.google.firebase:firebase-analytics-ktx:${Versions.firebaseAnalyticsVersion}"

    val mockito = "org.mockito:mockito-core:${Versions.mockitoVersion}"
    val junit = "junit:junit:${Versions.junitVersion}"
    val androidxTestRunner = "androidx.test:runner:${Versions.androidxTestRunnerVersion}"
    val androidxEspresso = "androidx.test.espresso:espresso-core:${Versions.androidxEspressoVersion}"
    val nhaarmanMockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.nhaarmanMockitoKotlinVersion}"
    val architectureTesting = "android.arch.core:core-testing:${Versions.architectureTestingVersion}"
}
