package io.hvam.android.githubapisample.ui.repo.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.hvam.android.githubapi.model.RepoInfo
import io.hvam.android.githubapisample.databinding.RepoItemBinding
import timber.log.Timber

class RepoAdapter(
    var repos: List<RepoInfo> = emptyList(),
    private val callback: ClickListener
) : RecyclerView.Adapter<RepoAdapter.RepoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        Timber.i("onCreateViewHolder")
        val binding = RepoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        Timber.i("onBindViewHolder position=$position")
        holder.bind(repos[position])
    }

    override fun getItemCount(): Int = repos.size

    inner class RepoViewHolder(
        private val binding: RepoItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        val name: TextView = TextView(itemView.context)
        fun bind(repo: RepoInfo) {
            Timber.i("RepoViewHolder.bind repo=${repo.name}")
            binding.repo = repo
            binding.callback = callback
            binding.executePendingBindings()
        }
    }

    interface ClickListener {
        fun onItemClicked(repo: RepoInfo)
    }
}
