package io.hvam.android.githubapisample.ui.repo.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import io.hvam.android.githubapisample.databinding.FragmentRepoListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

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
        return binding.root
    }
}
