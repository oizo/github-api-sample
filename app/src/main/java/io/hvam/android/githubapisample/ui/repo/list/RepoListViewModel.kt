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
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import timber.log.Timber

class RepoListViewModel : ViewModel(), KoinComponent, DefaultLifecycleObserver {

    private val githubRepo: GitHubRepository by inject()
    val onNavigate = MutableLiveData(false)
    val status = MutableLiveData<RepoListState>(RepoListState.Loading)

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        viewModelScope.launch {
            status.value = RepoListState.Loading
            status.value = githubRepo.repositories("square").map {
                RepoListState.Success(it)
            }.getOrElse { RepoListState.Error(it) }
        }
    }

    fun onItemClicked() {
        Timber.i("onItemClicked")
        onNavigate.value = true
        onNavigate.value = false
    }
}

sealed class RepoListState {
    object Loading : RepoListState()
    data class Error(val error: String) : RepoListState()
    data class Success(val repoInfoListState: List<RepoInfo>) : RepoListState()
}
