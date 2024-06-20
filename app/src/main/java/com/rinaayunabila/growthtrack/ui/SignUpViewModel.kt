package com.rinaayunabila.growthtrack.ui

import androidx.lifecycle.ViewModel
import com.rinaayunabila.growthtrack.data.UserRepository

class SignUpViewModel(private val repository: UserRepository): ViewModel() {

    fun userSignUp(name: String, email: String, password: String) =
        repository.userSignUp(name, email, password)
}