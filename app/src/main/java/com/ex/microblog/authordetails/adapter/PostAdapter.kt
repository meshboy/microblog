package com.ex.microblog.authordetails.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ex.microblog.core.data.post.domain.Post
import com.ex.microblog.databinding.PostContentListItemBinding

/**
 *@Post meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-23
 */
class PostAdapter  : ListAdapter<Post, PostAdapter.PostViewHolder>(DiffCallback) {

    private lateinit var onClickListener: OnClickListener

    fun setListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)

        holder.setListener {
            onClickListener.onClick(post)
        }
    }

    class PostViewHolder(private val binding: PostContentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.post = post
            binding.executePendingBindings()
        }

        fun setListener(listener: (v: View) -> Unit) {
            binding.cardLayout.setOnClickListener(listener)
        }

        companion object {
            fun from(parent: ViewGroup): PostViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = PostContentListItemBinding.inflate(layoutInflater, parent, false)
                return PostViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Post]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (post: Post) -> Unit) {
        fun onClick(post: Post) = clickListener(post)
    }
}