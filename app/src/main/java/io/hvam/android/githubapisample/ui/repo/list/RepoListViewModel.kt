package io.hvam.android.githubapisample.ui.repo.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class RepoListViewModel : ViewModel() {

    val onNavigate = MutableLiveData(false)

    fun onItemClicked() {
        Timber.i("onItemClicked")
        onNavigate.value = true
        onNavigate.value = false
    }
}
