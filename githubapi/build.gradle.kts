plugins {
    id(Plugins.Android.library)
    id(Plugins.kotlin_android)
}

val gitHub = GitHubProperties(rootProject)

android {
    compileSdk = AndroidBuild.compileSdkVersion

    defaultConfig {
        minSdk = AndroidBuild.minSdkVersion
        targetSdk = AndroidBuild.targetSdkVersion
        testInstrumentationRunner = Dependencies.Android.X.Test.junit_runner
        buildConfigField("String", "GITHUB_USER", "\"${gitHub.user}\"")
        buildConfigField("String", "GITHUB_PAT", "\"${gitHub.pat}\"")
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
        sourceCompatibility = Java.version
        targetCompatibility = Java.version
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Dependencies.Square.okhttp)
    implementation(Dependencies.Square.retrofit)
    implementation(Dependencies.Square.gson_converter)
    implementation(Dependencies.Android.X.ktx)
    implementation(Dependencies.Android.X.appcompat)
    // Material design components
    implementation(Dependencies.Google.material)
    // Kotlin coroutines
    implementation(Dependencies.Kotlin.X.Coroutines.core)
    implementation(Dependencies.Kotlin.X.Coroutines.android)
    api(Dependencies.kotlin_result)
    // Koin - Dependency injection
    implementation(Dependencies.Koin.android)
    // Timber - Logging
    implementation(Dependencies.timber)
    // Junit - Unit test framework
    testImplementation(Dependencies.junit)
    // Integration test
    androidTestImplementation(Dependencies.Android.X.Test.junit)
    androidTestImplementation(Dependencies.Android.X.Test.espresso)
}
