package io.hvam.android.githubapisample.ui.repo.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class RepoDetailsViewModel : ViewModel() {

    val status = MutableLiveData<Status>(Status.Loading)

    fun fetchRepoDetails(repoName: String) {
        viewModelScope.launch {
            status.value = Status.Loading
            Thread.sleep(1000)
            status.value = if (Random.nextBoolean()) {
                Status.Success("Yaii! $repoName")
            } else {
                Status.Error("Buuh! $repoName")
            }
        }
    }
}

sealed class Status {
    object Loading: Status()
    data class Error(val msg: String): Status()
    data class Success(val details: String): Status()
}