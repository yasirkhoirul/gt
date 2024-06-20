package com.rinaayunabila.growthtrack

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rinaayunabila.growthtrack.data.DataItem
import com.rinaayunabila.growthtrack.data.UserModel
import com.rinaayunabila.growthtrack.data.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: UserRepository): ViewModel() {
    fun logout() {
        viewModelScope.launch {
            repository.logout()
        }
    }

    fun getUserModel(): LiveData<UserModel> {
        return repository.getUserData()
    }

    fun getListArticle(): LiveData<PagingData<DataItem>> {
        return repository.getArticle().cachedIn(viewModelScope)
    }
}