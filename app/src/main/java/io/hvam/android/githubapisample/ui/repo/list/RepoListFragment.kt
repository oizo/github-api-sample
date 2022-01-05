package io.hvam.android.githubapisample.ui.repo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import io.hvam.android.githubapisample.R
import io.hvam.android.githubapisample.databinding.FragmentRepoListBinding
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class RepoListFragment : Fragment() {

    private val viewModel: RepoListViewModel by viewModel()
    private val gson: Gson by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // binding might leak, should be taken care of
        val binding = FragmentRepoListBinding.inflate(inflater, container, false)
        binding.vm = viewModel
        viewModel.showDetails.observe(viewLifecycleOwner) { repo ->
            Timber.i("show: repo=${repo?.name}")
            repo?.let {
                val jsonString = gson.toJson(repo)
                val direction = RepoListFragmentDirections.toRepoDetails(jsonString)
                findNavController().navigate(direction)
            }
        }
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            Timber.i("repoLoadStatus state=${state.javaClass.simpleName}")
            when (state) {
                is UiState.Loading -> {
                    binding.status.setText(R.string.loading)
                    binding.status.visibility = View.VISIBLE
                    binding.recyclerView.adapter = RepoAdapter(listOf(), viewModel.onClick)
                }
                is UiState.Error -> {
                    binding.status.text = state.error
                    binding.status.visibility = View.VISIBLE
                    binding.recyclerView.adapter = RepoAdapter(listOf(), viewModel.onClick)
                }
                is UiState.Success -> {
                    binding.status.visibility = View.GONE
                    binding.recyclerView.adapter = RepoAdapter(state.repoInfoList, viewModel.onClick)
                }
            }
        }
        lifecycle.addObserver(viewModel)
        return binding.root
    }
}
