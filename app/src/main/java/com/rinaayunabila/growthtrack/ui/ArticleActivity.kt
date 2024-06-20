package com.rinaayunabila.growthtrack.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rinaayunabila.growthtrack.ViewModelFactory
import com.rinaayunabila.growthtrack.databinding.ActivityArticleBinding

class ArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleBinding
    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setUpViewModel()

        binding.rvArticle.layoutManager = LinearLayoutManager(this)
        articleAdapter = ArticleAdapter()
        binding.rvArticle.adapter = articleAdapter.withLoadStateFooter(
            footer = LoadingAdapter { articleAdapter.retry() }
        )
        binding.rvArticle.setHasFixedSize(true)

        loadStories()
    }

    private fun loadStories() {
        showLoad(true)  // Show loading indicator
        articleViewModel.getListArticle().observe(this@ArticleActivity) { pagingData ->
            articleAdapter.submitData(lifecycle, pagingData)
            showLoad(false)  // Hide loading indicator
        }
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        articleViewModel = ViewModelProvider(this, factory)[ArticleViewModel::class.java]
    }

    private fun showLoad(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}