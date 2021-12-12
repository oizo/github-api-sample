buildscript {
    repositories {
        addRepos(this)
    }
    dependencies {
        classpath(Dependencies.Android.gradle_plugin)
        classpath(Dependencies.Kotlin.gradle_plugin)
        classpath(Dependencies.Android.X.Navigation.safe_args_plugin)
    }
}

plugins {
    id(Plugins.Android.application) version Versions.android_gradle_plugin apply false
    id(Plugins.Android.library) version Versions.android_gradle_plugin apply false
    id(Plugins.Kotlin.android) version Versions.kotlin apply false
    id(Plugins.ktlint) version Versions.ktlint apply false
}

subprojects {
    apply(plugin = Plugins.ktlint)
}

tasks {
    val clean by registering(Delete::class) {
        delete(rootProject.buildDir)
    }
}
