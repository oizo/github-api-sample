plugins {
    id(Plugins.android_application)
    id(Plugins.kotlin_android)
    id(Plugins.navigation_safe_args)
}

android {
    compileSdkVersion(AndroidBuild.compileSdkVersion)
    defaultConfig {
        applicationId = "io.hvam.android.githubapisample"
        minSdkVersion(AndroidBuild.minSdkVersion)
        targetSdkVersion(AndroidBuild.targetSdkVersion)
        versionCode = versionCode()
        versionName = versionName()
        testInstrumentationRunner = Dependencies.Android.X.Test.junit_runner
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }
    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
    }
    compileOptions {
        sourceCompatibility(Java.version)
        targetCompatibility(Java.version)
    }
    kotlinOptions {
        jvmTarget = Java.version.toString()
    }
}

dependencies {
    implementation(Dependencies.Kotlin.stdlib_jdk8)
    implementation(Dependencies.Android.X.ktx)
    implementation(Dependencies.Android.X.appcompat)
    implementation(Dependencies.Android.X.constraintlayout)
    implementation(Dependencies.Android.X.Lifecycle.livedata)
    implementation(Dependencies.Android.X.Lifecycle.viewmodel)
    // Kotlin Coroutines
    implementation(Dependencies.Kotlin.X.Coroutines.core)
    implementation(Dependencies.Kotlin.X.Coroutines.android)
    // Material design components
    implementation(Dependencies.Google.material)
    // Navigation component
    implementation(Dependencies.Android.X.Navigation.fragment_ktx)
    implementation(Dependencies.Android.X.Navigation.ui_ktx)
    // Koin - Dependency injection
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.viewmodel)
    implementation(Dependencies.Koin.scope)
    // Timber - Logging
    implementation(Dependencies.timber)
    // Junit - Unit test framework
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.Kotlin.junit)
    // Mockk.io - Mocking framework
    testImplementation(Dependencies.Mockk.android)
}