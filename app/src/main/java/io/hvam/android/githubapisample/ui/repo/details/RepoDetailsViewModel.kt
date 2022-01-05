package io.hvam.android.githubapisample.ui.repo.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import io.hvam.android.githubapi.model.RepoInfo
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RepoDetailsViewModel : ViewModel(), KoinComponent {

    val status = MutableLiveData<UiState>(UiState.Loading)
    private val gson: Gson by inject()

    fun fetchRepoDetails(repoJson: String) {
        viewModelScope.launch {
            status.value = UiState.Loading
            val repo = gson.fromJson(repoJson, RepoInfo::class.java)
            status.value = UiState.Success(repo)
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Error(val msg: String) : UiState()
    data class Success(val repo: RepoInfo) : UiState()
}
