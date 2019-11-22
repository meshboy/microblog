package com.ex.microblog.authors.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ex.microblog.core.data.author.domain.Author
import com.ex.microblog.databinding.AuthorContentListItemBinding

/**
 *@author meshileya seun <mesh@kudi.ai/>
 *@date 2019-11-22
 */
class AuthorAdapter : ListAdapter<Author, AuthorAdapter.AuthorViewHolder>(DiffCallback) {

    private lateinit var onClickListener: OnClickListener

    fun setListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorViewHolder {
        return AuthorViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: AuthorViewHolder, position: Int) {
        val author = getItem(position)
        holder.bind(author)

        holder.setListener {
            onClickListener.onClick(author)
        }
    }

    class AuthorViewHolder(private val binding: AuthorContentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(Author: Author) {
            binding.author = Author
            binding.executePendingBindings()
        }

        fun setListener(listener: (v: View) -> Unit) {
            binding.cardLayout.setOnClickListener(listener)
        }

        companion object {
            fun from(parent: ViewGroup): AuthorViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = AuthorContentListItemBinding.inflate(layoutInflater, parent, false)
                return AuthorViewHolder(binding)
            }
        }
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Author]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Author>() {
        override fun areItemsTheSame(oldItem: Author, newItem: Author): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Author, newItem: Author): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (Author: Author) -> Unit) {
        fun onClick(Author: Author) = clickListener(Author)
    }
}