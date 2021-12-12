package io.hvam.android.githubapisample.di

import io.hvam.android.githubapisample.ui.repo.details.RepoDetailsViewModel
import io.hvam.android.githubapisample.ui.repo.list.RepoListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {
    viewModel { RepoListViewModel() }
    viewModel { RepoDetailsViewModel() }
}
