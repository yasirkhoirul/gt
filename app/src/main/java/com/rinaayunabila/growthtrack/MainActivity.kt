package com.rinaayunabila.growthtrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rinaayunabila.growthtrack.databinding.ActivityMainBinding
import com.rinaayunabila.growthtrack.ui.ArticleActivity
import com.rinaayunabila.growthtrack.ui.OnBoardingActivity
import com.rinaayunabila.growthtrack.ui.SplashScreenActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setUpViewModel()

        mainViewModel.getUserModel().observe(this@MainActivity) { user ->
            if (user.token.isNotEmpty()) { // Check if token is not empty
                // Token is present, user is logged in
            } else {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
            }
        }

        binding.btnLogout.setOnClickListener {
            showDialog()
        }
        binding.btnAdd.setOnClickListener {
            val moveToAdd = Intent(this, ArticleActivity::class.java)
            startActivity(moveToAdd)
        }
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        mainViewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(resources.getString(R.string.logout))
        builder.setMessage("Are you sure you want to log out?")
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            mainViewModel.logout()
            startActivity(Intent(this, SplashScreenActivity::class.java))
        }
        builder.setNegativeButton(android.R.string.cancel, null)
        builder.show()
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    private fun showLoad(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}