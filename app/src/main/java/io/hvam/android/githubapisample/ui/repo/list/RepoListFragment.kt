package io.hvam.android.githubapisample.ui.repo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.hvam.android.githubapisample.databinding.FragmentRepoListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RepoListFragment : Fragment() {

    private val viewModel: RepoListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding might leak, should be taken care of
        val binding = FragmentRepoListBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        viewModel.onNavigate.observe(viewLifecycleOwner) {
            if (it) {
                val direction = RepoListFragmentDirections.toRepoDetails("unknown")
                findNavController().navigate(direction)
            }
        }
        viewModel.status.observe(viewLifecycleOwner) {
            val text = when (it) {
                is RepoListState.Loading -> "GitHub loading..."
                is RepoListState.Success -> "GitHub success: ${it.repoInfoListState[0].name}"
                is RepoListState.Error -> "GitHub error: ${it.error}"
            }
            Timber.i(text)
            binding.list.text = text
        }
        lifecycle.addObserver(viewModel)
        return binding.root
    }
}
