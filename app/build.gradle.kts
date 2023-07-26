plugins {
    id(Plugins.ANDROID_APPLICATION)
    id(Plugins.KOTLIN_ANDROID)
    id(Plugins.KOTLIN_PARCELIZE)
    id(Plugins.KOTLIN_KAPT)
}

android {
    compileSdk = Android.TARGET_SDK

    defaultConfig {
        applicationId = Android.APPLICATION_ID
        minSdk = Android.MIN_SDK
        targetSdk = Android.TARGET_SDK
        versionCode = Android.VERSION_CODE
        versionName = Android.VERSION_NAME

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "BASE_URL", "\"https://api.vk.com/method/\"")
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
    kotlinOptions {
        jvmTarget = Android.JVM_TARGET
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.COMPOSE_VERSION
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = Android.APPLICATION_ID
}

dependencies {

    implementation(Dependencies.CORE_KTX)

    //Compose
    implementation(Dependencies.COMPOSE_UI)
    implementation(Dependencies.COMPOSE_PREVIEW)
    implementation(Dependencies.COMPOSE_MATERIAL)
    implementation(Dependencies.COMPOSE_MATERIAL_3)
    implementation(Dependencies.COMPOSE_ACTIVITY)
    implementation(Dependencies.COMPOSE_NAVIGATION)

    //Network
    implementation(Dependencies.RETROFIT)
    implementation(Dependencies.LOGGING_INTERCEPTOR)
    implementation(Dependencies.GSON)

    //Vk sdk
    implementation(Dependencies.VK_CORE)
    implementation(Dependencies.VK_API)

    //Dagger
    implementation(Dependencies.DAGGER)
    kapt(Dependencies.DAGGER_COMPILER)

    //Image
    implementation(Dependencies.COIL)
    implementation(Dependencies.ZOOMABLE_IMAGE)

    //Swiperefresh
    implementation(Dependencies.SWIPEREFRESH)

    //Shimmer
    implementation(Dependencies.SHIMMER)

    //Test
    testImplementation(Dependencies.JUNIT)
    androidTestImplementation(Dependencies.JUNIT_ANDROID)
    androidTestImplementation(Dependencies.ESPRESSO)
    androidTestImplementation(Dependencies.JUNIT_COMPOSE)
    debugImplementation(Dependencies.COMPOSE_UI_TOOLING)
    debugImplementation(Dependencies.COMPOSE_UI_TEST_MANIFEST)
}