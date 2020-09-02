package com.android.gitreposapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.gitreposapp.R
import com.android.gitreposapp.models.RepositoryResponseItem
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_repository_preview.view.*

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoryItemViewHolder>() {

    inner class RepositoryItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<RepositoryResponseItem>(){

        override fun areItemsTheSame(
            oldItem: RepositoryResponseItem,
            newItem: RepositoryResponseItem
        ): Boolean {
            return oldItem.htmlUrl == newItem.htmlUrl
        }

        override fun areContentsTheSame(
            oldItem: RepositoryResponseItem,
            newItem: RepositoryResponseItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryItemViewHolder {
        return RepositoryItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_repository_preview,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: RepositoryItemViewHolder, position: Int) {
        val repository = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(repository.owner.avatarUrl).into(item_image_repository)
            item_repository_owner.text = repository.owner.login
            item_repository_name.text = repository.name

            setOnClickListener {
                onItemClickListener?.let{it(repository)}
            }
        }
    }

    private var onItemClickListener: ((RepositoryResponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (RepositoryResponseItem) -> Unit){
        onItemClickListener = listener
    }
}