package com.ex.microblog.postdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ex.microblog.core.data.comments.domain.Comment
import com.ex.microblog.databinding.CommentContentListItemBinding

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-24
 */

class CommentAdapter : ListAdapter<Comment, CommentAdapter.CommentViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = getItem(position)
        holder.bind(comment)
    }

    class CommentViewHolder(private val binding: CommentContentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(comment: Comment) {
            binding.comment = comment
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): CommentViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CommentContentListItemBinding.inflate(layoutInflater, parent, false)
                return CommentViewHolder(
                    binding
                )
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Comment]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Comment>() {
        override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean {
            return oldItem.userName == newItem.userName
        }
    }
}