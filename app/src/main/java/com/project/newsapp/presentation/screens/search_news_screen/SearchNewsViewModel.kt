package com.project.newsapp.presentation.screens.search_news_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newsapp.domain.data.NewsQueryState
import com.project.newsapp.domain.network.NetworkConnection
import com.project.newsapp.domain.usecase.FetchNewsOnQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val fetchNewsOnQuery: FetchNewsOnQuery
) : ViewModel() {

    var newsQueryState by mutableStateOf(NewsQueryState())
        private set


    fun updateQueryText(newText: String) {
        newsQueryState = newsQueryState.copy(newsQueryText = newText)
    }

    fun fetchNews(query: String){
        updateQueryText(query)
        viewModelScope.launch {
            val newsQueryStateFlow=fetchNewsOnQuery(query)
            newsQueryStateFlow.collect {newsStateData->
                newsQueryState = newsStateData.copy(newsQueryText = query)
            }
        }
    }

}