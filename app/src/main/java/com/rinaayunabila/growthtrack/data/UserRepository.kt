package com.rinaayunabila.growthtrack.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rinaayunabila.growthtrack.data.service.ApiResult
import com.rinaayunabila.growthtrack.data.service.ApiService
import kotlinx.coroutines.Dispatchers

class UserRepository private constructor(
    private val pref: UserPreference,
    private val apiService: ApiService
) {

    fun userSignUp(name: String, email: String, password: String): LiveData<ApiResult<SignUpResponse>> = liveData(Dispatchers.IO) {
        emit(ApiResult.Loading)
        try {
            val response = apiService.register(SignUpRequest(name, email, password))
            emit(ApiResult.Success(response))
        } catch (e: Exception) {
            Log.e("SignUp", "Sign up failed", e)
            emit(ApiResult.Error(e.message.toString()))
        }
    }

    fun userSignIn(email: String, password: String): LiveData<ApiResult<LoginResponse>> = liveData(Dispatchers.IO) {
        emit(ApiResult.Loading)
        try {
            val response = apiService.login(SignInRequest(email, password))
            if (response.success) {
                // Save user data after successful login
                val userModel = UserModel(response.data.email, response.data.uid, response.token)
                saveUserData(userModel)
            }
            emit(ApiResult.Success(response))
        } catch (e: Exception) {
            Log.e("SignIn", "Sign in failed", e)
            emit(ApiResult.Error(e.message.toString()))
        }
    }

    fun getUserData(): LiveData<UserModel> {
        return pref.getUserData().asLiveData()
    }

    suspend fun saveUserData(userEntity: UserModel) {
        pref.saveUserData(userEntity)
    }

    suspend fun logout() {
        pref.logout()
    }

    fun getArticle(): LiveData<PagingData<DataItem>> {
        return Pager(
            config = PagingConfig(pageSize = 5, enablePlaceholders = false),
            pagingSourceFactory = { StoryPage(apiService, pref) }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(preferences: UserPreference, apiService: ApiService): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(preferences, apiService).also { instance = it }
            }
        }
    }
}