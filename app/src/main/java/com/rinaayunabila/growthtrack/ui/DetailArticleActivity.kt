package com.rinaayunabila.growthtrack.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rinaayunabila.growthtrack.data.DataItem
import com.rinaayunabila.growthtrack.databinding.ActivityDetailArticleBinding

class DetailArticleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailArticleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val detail = intent.getParcelableExtra<DataItem>(DETAILS)
        detail?.let { displayDetail(it) }
    }

    private fun displayDetail(detail: DataItem) {
        Glide.with(this)
            .load(detail.imageURL)
            .into(binding.ivPictureDetail)
        binding.tvTittleDetail.text = detail.title
        binding.tvDescDetail.text = detail.content
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val DETAILS = "details"
    }
}