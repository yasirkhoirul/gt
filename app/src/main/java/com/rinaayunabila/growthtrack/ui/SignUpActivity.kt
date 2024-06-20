package com.rinaayunabila.growthtrack.ui

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.rinaayunabila.growthtrack.ViewModelFactory
import com.rinaayunabila.growthtrack.data.service.ApiResult
import com.rinaayunabila.growthtrack.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var signUpViewModel: SignUpViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpViewModel()
        playAnimation()

        binding.btnSignup.setOnClickListener {
            val name = binding.nameSignup.text.toString()
            val email = binding.emailSignup.text.toString()
            val password = binding.passSignup.text.toString()
            if (name.isEmpty()) {
                binding.nameSignup.error = "Input Name"
            } else if (email.isEmpty()) {
                binding.emailSignup.error = "Input Email"
            } else if (password.isEmpty()) {
                binding.passSignup.error = "Input Password"
            } else if(password.length<8){
                binding.passSignup.requestFocus()
            }
            signUpViewModel.userSignUp(name, email, password).observe(this) {
                when (it) {
                    is ApiResult.Success -> {
                        showLoad(false)
                        showDialog(SUCCESS)
                        startActivity(Intent(this, SignInActivity::class.java))
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
        binding.tvLoginSignup.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logoAppText, View.TRANSLATION_X, -40f, 40f).apply {
            duration = 4000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

    }

    private fun showDialog(mode: String) {
        val builder = AlertDialog.Builder(this)
        if (mode == FAIL) {
            val title = FAIL
            val message = "Registration failed, please try again"
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok) { _, _ -> }
            builder.show()
        } else if (mode == SUCCESS) {
            val title = SUCCESS
            val message = "Register successfully, continue logging in!"
            builder.setTitle(title)
            builder.setMessage(message)
            builder.setPositiveButton(android.R.string.ok) { _, _ ->
                goToSignIn()
            }
            builder.show()
        }
    }

    private fun goToSignIn() {
        Toast.makeText(this, "Register success!", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }

    private fun setUpViewModel() {
        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        signUpViewModel = ViewModelProvider(this, factory)[SignUpViewModel::class.java]
    }
    companion object {
        const val FAIL = "fail"
        const val SUCCESS = "success"
    }
    private fun showLoad(isLoading: Boolean) {
        binding.progressBarSignUp.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}