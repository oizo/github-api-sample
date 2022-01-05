package io.hvam.android.githubapisample.di

import com.google.gson.GsonBuilder
import io.hvam.android.githubapisample.ui.repo.details.RepoDetailsViewModel
import io.hvam.android.githubapisample.ui.repo.list.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { RepoListViewModel() }
    viewModel { RepoDetailsViewModel() }
    single { GsonBuilder().create() }
}
