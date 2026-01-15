package com.project.newsapp.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newsapp.domain.data.NewsDataState
import com.project.newsapp.domain.usecase.FetchNewsOnCategory
import com.project.newsapp.domain.usecase.FetchTopHeadlines
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTopHeadlines: FetchTopHeadlines,
    private val fetchNewsOnCategory: FetchNewsOnCategory
): ViewModel() {

    var state by mutableStateOf(NewsDataState())
        private set



     fun fetchData() {
        val flowOfNewsData=fetchTopHeadlines("us")
        viewModelScope.launch {
            flowOfNewsData.collect { newsDataState ->
                state = NewsDataState(loading = newsDataState.loading, data = newsDataState.data, error = newsDataState.error)
            }
        }
    }

    fun fetchBasedOnCategory(category: String){
        val flowOfNewsData=fetchNewsOnCategory(category)
        viewModelScope.launch {
            flowOfNewsData.collect { newsDataState ->
                state = NewsDataState(loading = newsDataState.loading, data = newsDataState.data, error = newsDataState.error)
            }
        }
    }


}