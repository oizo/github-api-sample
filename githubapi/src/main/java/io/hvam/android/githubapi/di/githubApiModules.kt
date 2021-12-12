package io.hvam.android.githubapi.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.hvam.android.githubapi.BuildConfig
import io.hvam.android.githubapi.GitHubApi
import io.hvam.android.githubapi.GitHubRepository
import kotlinx.coroutines.Dispatchers
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.scope.get
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

private const val GITHUB_BASE_URL = "https://api.github.com/"

val githubApiModules = module {
    single { provideGson() }
    single { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideGitHubApi(get()) }
    single { Dispatchers.IO }
    single { GitHubRepository(get(), get()) }
}

private fun provideGson(): Gson = GsonBuilder().create()

private fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
    addInterceptor { chain ->
        val request = chain.request().newBuilder().apply {
            val credentials = Credentials.basic(BuildConfig.GITHUB_USER, BuildConfig.GITHUB_PAT)
            addHeader("Authorization", credentials)
        }.build()
        chain.proceed(request)
    }
    if (BuildConfig.DEBUG) {
        val debugIntercaptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag("GitHubApi").i(message)
            }
        }).apply { level = HttpLoggingInterceptor.Level.BODY }
        addInterceptor(debugIntercaptor)
    }
}.build()

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    gson: Gson
): Retrofit = Retrofit.Builder().apply {
    baseUrl(GITHUB_BASE_URL)
    client(okHttpClient)
    addConverterFactory(GsonConverterFactory.create(gson))
}.build()

private fun provideGitHubApi(
    retrofit: Retrofit
): GitHubApi = retrofit.create(GitHubApi::class.java)
