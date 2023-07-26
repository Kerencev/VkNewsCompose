object Dependencies {

    private const val CORE_KTX_VERSION = "1.10.1"
    const val CORE_KTX = "androidx.core:core-ktx:$CORE_KTX_VERSION"

    //Compose
    private const val COMPOSE_MATERIAL_VERSION = "1.1.1"
    private const val COMPOSE_ACTIVITY_VERSION = "1.7.2"
    private const val COMPOSE_NAVIGATION_VERSION = "2.6.0"
    const val COMPOSE_VERSION = "1.4.3"
    const val COMPOSE_UI = "androidx.compose.ui:ui:$COMPOSE_VERSION"
    const val COMPOSE_PREVIEW = "androidx.compose.ui:ui-tooling-preview:$COMPOSE_VERSION"
    const val COMPOSE_MATERIAL = "androidx.compose.material:material:$COMPOSE_VERSION"
    const val COMPOSE_MATERIAL_3 = "androidx.compose.material3:material3:$COMPOSE_MATERIAL_VERSION"
    const val COMPOSE_ACTIVITY = "androidx.activity:activity-compose:$COMPOSE_ACTIVITY_VERSION"
    const val COMPOSE_NAVIGATION = "androidx.navigation:navigation-compose:$COMPOSE_NAVIGATION_VERSION"

    //Network
    private const val RETROFIT_VERSION = "2.9.0"
    private const val LOGGING_INTERCEPTOR_VERSION = "4.9.3"
    private const val GSON_VERSION = "2.10"
    const val RETROFIT = "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION"
    const val LOGGING_INTERCEPTOR = "com.squareup.okhttp3:logging-interceptor:$LOGGING_INTERCEPTOR_VERSION"
    const val GSON = "com.google.code.gson:gson:$GSON_VERSION"

    //Vk sdk
    private const val VK_SDK_VERSION = "4.0.1"
    const val VK_CORE = "com.vk:android-sdk-core:$VK_SDK_VERSION"
    const val VK_API = "com.vk:android-sdk-api:$VK_SDK_VERSION"

    //Dagger
    private const val DAGGER_VERSION = "2.43.2"
    const val DAGGER = "com.google.dagger:dagger:$DAGGER_VERSION"
    const val DAGGER_COMPILER = "com.google.dagger:dagger-compiler:$DAGGER_VERSION"

    //Image
    private const val COIL_VERSION = "2.1.0"
    private const val ZOOMABLE_IMAGE_VERSION = "0.5.0"
    const val COIL = "io.coil-kt:coil-compose:$COIL_VERSION"
    const val ZOOMABLE_IMAGE = "com.github.SmartToolFactory:Compose-Zoom:$ZOOMABLE_IMAGE_VERSION"

    //Swiperefresh
    private const val SWIPEREFRESH_VERSION = "0.24.13-rc"
    const val SWIPEREFRESH = "com.google.accompanist:accompanist-swiperefresh:$SWIPEREFRESH_VERSION"

    //Shimmer
    private const val SHIMMER_VERSION = "1.0.5"
    const val SHIMMER = "com.valentinilk.shimmer:compose-shimmer:$SHIMMER_VERSION"

    //Test
    private const val JUNIT_VERSION = "4.13.2"
    private const val JUNIT_ANDROID_VERSION = "1.1.5"
    private const val ESPRESSO_VERSION = "3.5.1"
    const val JUNIT = "junit:junit:$JUNIT_VERSION"
    const val JUNIT_ANDROID = "androidx.test.ext:junit:$JUNIT_ANDROID_VERSION"
    const val ESPRESSO = "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION"
    const val JUNIT_COMPOSE = "androidx.compose.ui:ui-test-junit4:$COMPOSE_VERSION"
    const val COMPOSE_UI_TOOLING = "androidx.compose.ui:ui-tooling:$COMPOSE_VERSION"
    const val COMPOSE_UI_TEST_MANIFEST = "androidx.compose.ui:ui-test-manifest:$COMPOSE_VERSION"
}