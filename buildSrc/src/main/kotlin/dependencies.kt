import org.gradle.api.JavaVersion
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

object AndroidBuild {
    const val compileSdkVersion = 31
    const val targetSdkVersion = 31
    const val minSdkVersion = 21
}

object Java {
    val version = JavaVersion.VERSION_1_8
}

const val versionMajor = 0
const val versionMinor = 1
const val versionPatch = 0
const val versionBuild = 0

fun versionCode(): Int = (versionMajor * 10000000) + (versionMinor * 100000) + (versionPatch * 1000) + versionBuild
fun versionName(): String = "$versionMajor.$versionMinor.$versionPatch-$versionBuild"

object Versions {
    const val kotlin = "1.5.32"
    const val android_gradle_plugin = "4.2.0"
    const val junit = "4.13.1"
    const val mockk = "1.12.1"
    const val appcompat = "1.4.0"
    const val ktx = "1.7.0"
    const val constraintlayout = "2.1.2"
    const val timber = "5.0.1"
    const val ktlint = "10.0.0"
    const val navigation = "2.4.0-beta02"
    const val coroutines = "1.5.2"
    const val koin = "3.1.4"
    const val google_material = "1.4.0"
}

object Plugins {
    const val android_application = "com.android.application"
    const val kotlin_android = "kotlin-android"
    const val ktlint = "org.jlleitschuh.gradle.ktlint"
    object Kotlin {
        const val android = "org.jetbrains.kotlin.android"
    }
    const val navigation_safe_args = "androidx.navigation.safeargs.kotlin"
    const val kapt = "kotlin-kapt"
}

object Dependencies {
    object Kotlin {
        val gradle_plugin = kotlin("gradle-plugin")
        val stdlib_jdk8 = kotlin("stdlib-jdk8")
        val junit = kotlin("test-junit")
        object X {
            object Coroutines {
                const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2"
                const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2"
            }
        }
    }
    object Android {
        const val gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
        object X {
            object Test {
                const val junit_runner = "androidx.test.runner.AndroidJUnitRunner"
            }
            const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
            const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
            const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
            object Lifecycle {
                const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:2.4.0"
                const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0"
            }
            object Navigation {
                const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
                const val ui_ktx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
                const val safe_args_plugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
            }
        }
    }
    object Google {
        const val material = "com.google.android.material:material:${Versions.google_material}"
    }
    object Koin {
        const val android = "io.insert-koin:koin-android:${Versions.koin}"

    }
    const val junit = "junit:junit:${Versions.junit}"
    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    object Mockk {
        const val android = "io.mockk:mockk-android:${Versions.mockk}"
    }
}

private fun kotlin(module: String, version: String? = Versions.kotlin): String =
        "org.jetbrains.kotlin:kotlin-$module${version?.let { ":$version" } ?: Versions.kotlin}"

/**
 * Add repositories to the [RepositoryHandler]
 */
fun addRepos(repoHandler: RepositoryHandler) {
    repoHandler.mavenCentral()
    repoHandler.google()
    repoHandler.maven("https://jitpack.io")
}
