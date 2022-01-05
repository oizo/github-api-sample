package io.hvam.android.githubapisample.ui.repo.list

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.getOrElse
import com.github.michaelbull.result.map
import io.hvam.android.githubapi.GitHubRepository
import io.hvam.android.githubapi.model.RepoInfo
import io.hvam.android.githubapisample.BuildConfig
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class RepoListViewModel : ViewModel(), KoinComponent, DefaultLifecycleObserver {

    private val githubRepo: GitHubRepository by inject()
    val showDetails = MutableLiveData<RepoInfo?>(null)
    val uiState = MutableLiveData<UiState>(UiState.Loading)

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        uiState.value = UiState.Loading
        viewModelScope.launch {
            // We should really consider pagination
            // https://developer.android.com/topic/libraries/architecture/paging/v3-overview
            uiState.value = githubRepo.repositories(BuildConfig.GITHUB_ORGANIZATION).map {
                UiState.Success(it)
            }.getOrElse {
                UiState.Error(it)
            }
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        showDetails.value = null
    }

    val onClick = object : RepoAdapter.ClickListener {
        override fun onItemClicked(repo: RepoInfo) {
            Timber.i("onItemClicked repo=${repo.name}")
            showDetails.value = repo
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Error(val error: String) : UiState()
    data class Success(val repoInfoList: List<RepoInfo>) : UiState()
}
