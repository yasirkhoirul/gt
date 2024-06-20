package com.rinaayunabila.growthtrack.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUserData(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            val token = preferences[TOKEN_KEY] ?: ""
            if (token.isNotEmpty()) {
                UserModel(
                    email = preferences[EMAIL_KEY] ?: "",
                    uid = preferences[UID_KEY] ?: "",
                    token = token
                )
            } else {
                UserModel(
                    email = "",
                    uid = "",
                    token = ""
                )
            }
        }
    }

    suspend fun saveUserData(user: UserModel) {
        dataStore.edit { preferences ->
            preferences[EMAIL_KEY] = user.email
            preferences[UID_KEY] = user.uid
            preferences[TOKEN_KEY] = user.token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
            preferences[EMAIL_KEY] = ""
            preferences[UID_KEY] = ""
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreference? = null
        private val EMAIL_KEY = stringPreferencesKey("email")
        private val UID_KEY = stringPreferencesKey("uid")
        private val TOKEN_KEY = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}