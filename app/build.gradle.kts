plugins {
    id(Plugins.Android.application)
    id(Plugins.kotlin_android)
    id(Plugins.navigation_safe_args)
    id(Plugins.kapt)
}

val gitHub = GitHubProperties(rootProject)

android {
    compileSdk = AndroidBuild.compileSdkVersion
    defaultConfig {
        applicationId = "io.hvam.android.githubapisample"
        minSdk = AndroidBuild.minSdkVersion
        targetSdk = AndroidBuild.targetSdkVersion
        versionCode = versionCode()
        versionName = versionName()
        testInstrumentationRunner = Dependencies.Android.X.Test.junit_runner
        buildConfigField("String", "GITHUB_ORGANIZATION", "\"${gitHub.org}\"")
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
    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(project(":githubapi"))
    implementation(Dependencies.Kotlin.stdlib_jdk8)
    implementation(Dependencies.Android.X.ktx)
    implementation(Dependencies.Android.X.appcompat)
    implementation(Dependencies.Android.X.constraintlayout)
    implementation(Dependencies.Android.X.Lifecycle.livedata)
    implementation(Dependencies.Android.X.Lifecycle.viewmodel)
    implementation(Dependencies.Google.gson)
    implementation(Dependencies.glide)
    // Kotlin Coroutines
    implementation(Dependencies.Kotlin.X.Coroutines.core)
    implementation(Dependencies.Kotlin.X.Coroutines.android)
    // Material design components
    implementation(Dependencies.Google.material)
    // Navigation component
    implementation(Dependencies.Android.X.Navigation.fragment_ktx)
    implementation(Dependencies.Android.X.Navigation.ui_ktx)
    // Koin - Dependency injection
    implementation(Dependencies.Koin.android)
    // Timber - Logging
    implementation(Dependencies.timber)
    // Junit - Unit test framework
    testImplementation(Dependencies.junit)
    testImplementation(Dependencies.Kotlin.junit)
    testImplementation(Dependencies.Android.X.Test.arch_core_testing)
    // Koin test
    testImplementation(Dependencies.Koin.test)
    // Mockk.io - Mocking framework
    testImplementation(Dependencies.Mockk.android)
    // Integration test
    androidTestImplementation(Dependencies.Android.X.Test.junit)
    androidTestImplementation(Dependencies.Android.X.Test.espresso)
}
