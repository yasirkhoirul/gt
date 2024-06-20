package com.rinaayunabila.growthtrack.ui

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rinaayunabila.growthtrack.MainActivity
import com.rinaayunabila.growthtrack.MainActivity2
import com.rinaayunabila.growthtrack.ViewModelFactory
import com.rinaayunabila.growthtrack.data.UserModel
import com.rinaayunabila.growthtrack.data.service.ApiResult
import com.rinaayunabila.growthtrack.databinding.ActivitySigninBinding

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySigninBinding
    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupViewModel()
        playAnimation()

        binding.tvofSignin.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.btnSignin.setOnClickListener {
            val email = binding.emailSignin.text.toString()
            val password = binding.cvPassSignin.text.toString()
            if (email.isEmpty()) {
                binding.emailSignin.error = "Input Email"
            } else if (password.isEmpty()) {
                binding.cvPassSignin.error = "Input Password"
            } else if (password.length < 8) {
                binding.cvPassSignin.requestFocus()
            } else {
                signInViewModel.userSignIn(email, password).observe(this) {
                    when (it) {
                        is ApiResult.Success -> {
                            showLoad(false)
                            val response = it.data
                            Log.d("login", response.data.email)
                            saveUserData(
                                UserModel(
                                    response.data.email,
                                    response.data.uid,
                                    response.token // Save the token here
                                )
                            )
                            startActivity(Intent(this, MainActivity2::class.java))
                            finishAffinity()
                        }
                        is ApiResult.Loading -> showLoad(true)
                        is ApiResult.Error -> {
                            showDialog(FAIL)
                            showLoad(false)
                        }
                    }
                }
            }
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imgSignin, View.TRANSLATION_X, -40f, 40f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val title = ObjectAnimator.ofFloat(binding.tvSignin, View.ALPHA, 1f).setDuration(1000)
        AnimatorSet().apply {
            playSequentially(title)
            start()
        }
    }

    private fun saveUserData(userData: UserModel) {
        signInViewModel.saveUserData(userData)
    }

    private fun setupViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        signInViewModel = ViewModelProvider(this, factory)[SignInViewModel::class.java]
    }

    companion object {
        const val FAIL = "fail"
        const val SUCCESS = "success"
    }

    private fun showLoad(isLoading: Boolean) {
        binding.progressBarLogin.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showDialog(mode: String) {
        val builder = AlertDialog.Builder(this)
        if (mode == FAIL) {
            builder.setTitle("Login Failed")
            builder.setMessage("Login failed, please try again")
            builder.setPositiveButton(android.R.string.ok) { _, _ -> }
            builder.show()
        } else if (mode == SUCCESS) {
            builder.setTitle("Login Success")
            builder.setMessage("Login successful, continue to the main screen!")
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                goToMain()
            }
            builder.show()
        }
    }

    private fun goToMain() {
        Toast.makeText(this, "Login success", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}