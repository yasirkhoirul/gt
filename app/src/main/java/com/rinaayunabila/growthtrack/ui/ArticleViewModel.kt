package com.rinaayunabila.growthtrack.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.rinaayunabila.growthtrack.data.DataItem
import com.rinaayunabila.growthtrack.data.UserRepository

class ArticleViewModel(private val repo: UserRepository) : ViewModel() {
    fun getListArticle(): LiveData<PagingData<DataItem>> {
        return repo.getArticle().cachedIn(viewModelScope)
    }
}