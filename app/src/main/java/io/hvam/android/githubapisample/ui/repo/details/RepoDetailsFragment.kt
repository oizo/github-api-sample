package io.hvam.android.githubapisample.ui.repo.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
            val repo: String = RepoDetailsFragmentArgs.fromBundle(it).repo
            viewModel.fetchRepoDetails(repo)
        }
    }
}

@BindingAdapter("repoDetails")
fun bindRepoDetails(view: TextView, status: Status) = when (status) {
    is Status.Loading -> view.text = "Loading"
    is Status.Success -> view.text = status.details
    is Status.Error -> view.text = status.msg
}
