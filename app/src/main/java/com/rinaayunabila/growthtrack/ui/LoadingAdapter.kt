package com.rinaayunabila.growthtrack.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rinaayunabila.growthtrack.databinding.ItemRetryBinding

class LoadingAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoadingAdapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRetryBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, retry)
    }

    class ViewHolder(private val binding: ItemRetryBinding, retry: () -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener { retry() }
        }

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                btnRetry.isVisible = loadState is LoadState.Error
                errorMsg.isVisible = loadState is LoadState.Error
                if (loadState is LoadState.Error) {
                    errorMsg.text = loadState.error.localizedMessage
                }
            }
        }
    }
}