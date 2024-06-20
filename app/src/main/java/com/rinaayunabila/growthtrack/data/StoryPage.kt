package com.rinaayunabila.growthtrack.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rinaayunabila.growthtrack.data.service.ApiService
import kotlinx.coroutines.flow.first
import java.lang.Exception

class StoryPage(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) : PagingSource<Int, DataItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataItem> {
        return try {
            val page = params.key ?: FIRST_PAGE_INDEX
            val uid = retrieveUidFromUserPreference()
            val responseData = apiService.getArticle(uid, page, params.loadSize).data

            LoadResult.Page(
                data = responseData,
                prevKey = if (page == FIRST_PAGE_INDEX) null else page - 1,
                nextKey = if (responseData.isNullOrEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private suspend fun retrieveUidFromUserPreference(): String {
        val userModel = userPreference.getUserData().first()
        return userModel.uid
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}