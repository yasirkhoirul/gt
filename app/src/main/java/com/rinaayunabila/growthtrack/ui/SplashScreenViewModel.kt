package com.rinaayunabila.growthtrack.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.rinaayunabila.growthtrack.data.UserPreference
import com.rinaayunabila.growthtrack.di.Injection

class SplashScreenViewModel(private val dataStore: UserPreference): ViewModel() {
    fun getUserSession() = dataStore.getUserData().asLiveData()
}

class ViewModelFactory(private val dataStore: UserPreference) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideDataStore(context))
            }.also { instance = it }
    }
}