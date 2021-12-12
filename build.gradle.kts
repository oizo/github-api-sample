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
    id(Plugins.ktlint) version Versions.ktlint
}

subprojects {
    apply(plugin = Plugins.ktlint)
}

tasks {
    val clean by registering(Delete::class) {
        delete(rootProject.buildDir)
    }
}
