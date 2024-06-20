package com.rinaayunabila.growthtrack.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.rinaayunabila.growthtrack.data.UserPreference
import com.rinaayunabila.growthtrack.data.service.ApiConfig
import com.rinaayunabila.growthtrack.data.UserRepository

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("mystoryapp")
object Injection {
    fun provideDataStore(context: Context): UserPreference {
        return UserPreference.getInstance(context.dataStore)
    }

    fun provideRepository(context: Context): UserRepository {
        val preferences = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(preferences, apiService)
    }
}