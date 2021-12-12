plugins {
    id(Plugins.Android.application)
    id(Plugins.kotlin_android)
}

android {
    compileSdk = AndroidBuild.compileSdkVersion

    defaultConfig {
        minSdk = AndroidBuild.minSdkVersion
        targetSdk = AndroidBuild.targetSdkVersion
        testInstrumentationRunner = Dependencies.Android.X.Test.junit_runner
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
    implementation(Dependencies.Android.X.ktx)
    implementation(Dependencies.Android.X.appcompat)
    // Material design components
    implementation(Dependencies.Google.material)
    // Junit - Unit test framework
    testImplementation(Dependencies.junit)
    // Integration test
    androidTestImplementation(Dependencies.Android.X.Test.junit)
    androidTestImplementation(Dependencies.Android.X.Test.espresso)
}
