object Dependencies {
    // Core
    const val core_ktx = "androidx.core:core-ktx:${Versions.kotlin_version}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat_version}"
    const val marital = "com.google.android.material:material:${Versions.material_version}"

    // Constraints Layout
    const val constraints_layout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraint_layout_version}"

    // Junit Test
    const val junit = "junit:junit:${Versions.junit_version}"

    // Kotlin
    const val kotlin_stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin_version}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines_android_version}"

    // Timber
    const val timber = "com.jakewharton.timber:timber:${Versions.timber_version}"

    // Navigation
    const val navigation_fragment =
        "androidx.navigation:navigation-fragment-ktx:${Versions.nav_version}"
    const val navigation_ui = "androidx.navigation:navigation-ui-ktx:${Versions.nav_version}"
    const val navigation_dynamic =
        "androidx.navigation:navigation-dynamic-features-fragment:${Versions.nav_version}"

    // DI
    const val hilt_android = "com.google.dagger:hilt-android:${Versions.hilt_version}"
    const val hilt_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt_version}"

    const val dagger_android = "com.google.dagger:dagger:${Versions.dagger_version}"
    const val dagger_compiler = "com.google.dagger:dagger-compiler:${Versions.dagger_version}"
    const val dagger_processor =
        "com.google.dagger:dagger-android-processor:${Versions.dagger_processor_version}"

    // Glide
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide_version}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide_version}"

    // Network
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit_version}"
    const val retrofit_moshi_converter =
        "com.squareup.retrofit2:converter-moshi:${Versions.moshi_converter_version}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okHttp_version}"
    const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp_version}"
    const val moshi = "com.squareup.moshi:moshi-kotlin:${Versions.moshi_version}"
    const val moshi_codegen = "com.squareup.moshi:moshi-kotlin-codegen:${Versions.moshi_version}"

    // Cache
    const val room_run_time = "androidx.room:room-runtime:${Versions.room_version}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.room_version}"
    const val room_rxjava2 = "androidx.room:room-rxjava2:${Versions.room_version}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.room_version}"

    // Swipe refresh layout
    const val swipe_refresh_layout =
        "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipe_refresh_layout}"
}