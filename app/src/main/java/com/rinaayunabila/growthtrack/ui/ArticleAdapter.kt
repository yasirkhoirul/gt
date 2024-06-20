package com.rinaayunabila.growthtrack.ui

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rinaayunabila.growthtrack.data.DataItem
import com.rinaayunabila.growthtrack.databinding.ItemArticleBinding

class ArticleAdapter : PagingDataAdapter<DataItem, ArticleAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemArticleBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { holder.bind(it) }
    }

    inner class MyViewHolder(private val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(story: DataItem) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(story.imageURL)
                    .into(ivItemArticle)
                tvTittle.text = story.title
            }
            itemView.setOnClickListener {
                val context = itemView.context
                val moveToDetail = Intent(context, DetailArticleActivity::class.java).apply {
                    putExtra(DetailArticleActivity.DETAILS, story)
                }
                val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    context as Activity,
                    Pair(binding.ivItemArticle, "article_photo"),
                    Pair(binding.tvTittle, "story_title")
                )
                context.startActivity(moveToDetail, optionsCompat.toBundle())
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}
