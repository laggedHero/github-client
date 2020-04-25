package net.laggedhero.githubclient.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.laggedhero.githubclient.databinding.FragmentSearchListItemBinding
import net.laggedhero.githubclient.domain.Repository

class SearchListAdapter : ListAdapter<Repository, SearchListAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentSearchListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) holder.bind(getItem(position))
        else holder.bind(payloads[0] as Bundle)
    }

    class ViewHolder(
        private val binding: FragmentSearchListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Repository) {
            binding.fragmentSearchListItemTitle.text = data.name.value
            binding.fragmentSearchListItemDescription.text = data.description.value
            bindAvatar(data.owner.avatarUrl.value)
        }

        fun bind(bundle: Bundle) {
            bundle.keySet().forEach { key ->
                if (key == KEY_AVATAR_URL) {
                    bindAvatar(bundle.getString(KEY_AVATAR_URL, ""))
                }
                if (key == KEY_REPOSITORY_TITLE) {
                    binding.fragmentSearchListItemTitle.text =
                        bundle.getString(KEY_REPOSITORY_TITLE, "")
                }
                if (key == KEY_REPOSITORY_DESCRIPTION) {
                    binding.fragmentSearchListItemDescription.text =
                        bundle.getString(KEY_REPOSITORY_DESCRIPTION, "")
                }
            }
        }

        private fun bindAvatar(avatarUrl: String) {
            Glide.with(binding.root)
                .load(avatarUrl)
                .circleCrop()
                .into(binding.fragmentSearchListItemOwnerAvatar)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }

            override fun getChangePayload(oldItem: Repository, newItem: Repository): Any? {
                val bundle = Bundle()
                if (oldItem.owner.avatarUrl != newItem.owner.avatarUrl) {
                    bundle.putString(KEY_AVATAR_URL, newItem.owner.avatarUrl.value)
                }
                if (oldItem.name != newItem.name) {
                    bundle.putString(KEY_REPOSITORY_TITLE, newItem.name.value)
                }
                if (oldItem.description != newItem.description) {
                    bundle.putString(KEY_REPOSITORY_DESCRIPTION, newItem.description.value)
                }
                return if (bundle.size() == 0) null else bundle
            }
        }

        const val KEY_AVATAR_URL = "KEY_AVATAR_URL"
        const val KEY_REPOSITORY_TITLE = "KEY_REPOSITORY_TITLE"
        const val KEY_REPOSITORY_DESCRIPTION = "KEY_REPOSITORY_DESCRIPTION"
    }
}