package io.hvam.android.githubapisample.ui.repo.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import io.hvam.android.githubapisample.R
import io.hvam.android.githubapisample.databinding.FragmentRepoDetailsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepoDetailsFragment : Fragment() {

    private val viewModel: RepoDetailsViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding might leak, should be taken care of
        val binding = FragmentRepoDetailsBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val repo: String = RepoDetailsFragmentArgs.fromBundle(it).repo
            viewModel.fetchRepoDetails(repo)
        }
    }
}

@BindingAdapter("repoName")
fun bindRepoDetails(view: TextView, state: UiState) = when (state) {
    is UiState.Loading -> view.setText(R.string.loading)
    is UiState.Success -> view.text = state.repo.name
    is UiState.Error -> view.text = state.msg
}

@BindingAdapter("ownerName")
fun bindOwnerName(view: TextView, state: UiState) = when (state) {
    is UiState.Loading -> view.text = ""
    is UiState.Success -> view.text = state.repo.owner.login
    is UiState.Error -> view.text = state.msg
}

@BindingAdapter("ownerUrl")
fun bindOwnerUrl(view: TextView, state: UiState) = when (state) {
    is UiState.Loading -> view.text = ""
    is UiState.Success -> view.text = state.repo.html_url
    is UiState.Error -> view.text = state.msg
}

@BindingAdapter("repoAvatar")
fun bindRepoAvatar(view: ImageView, state: UiState): Any = when (state) {
    is UiState.Loading -> view.setImageResource(R.drawable.ic_launcher_foreground)
    is UiState.Success -> {
        Glide.with(view)
            .load(state.repo.owner.avatar_url)
            .into(view)
    }
    is UiState.Error -> view.setImageResource(com.google.android.material.R.drawable.mtrl_ic_error)
}

@BindingAdapter("repoUrl")
fun bindRepoUrl(view: WebView, state: UiState) = when (state) {
    is UiState.Loading -> { /* no-op */ }
    is UiState.Success -> view.loadUrl(state.repo.html_url)
    is UiState.Error -> view.loadUrl("https://google.com/")
}
