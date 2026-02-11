package com.project.newsapp.presentation.screens.home_screen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newsapp.domain.data.Article
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.domain.data.NewsDataState
import com.project.newsapp.domain.usecase.FetchAllCategoryNews
import com.project.newsapp.domain.usecase.FetchTopHeadlines
import com.project.newsapp.domain.usecase.StoreAllNewsData
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchTopHeadlines: FetchTopHeadlines,
    private val fetchAllCategoryNews: FetchAllCategoryNews,
    private val storeAllNewsData: StoreAllNewsData
): ViewModel() {

    val TAG = "MAIN_VIEW_MODEL"

    val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.d(TAG, "Exception : ${throwable.message} ")
    }

    var newsState by mutableStateOf(NewsDataState())
        private set
    var topHeadLinesState by mutableStateOf(NewsDataState())
        private set
    var selectedCategory by mutableStateOf("business")
        private set


    init {
        initializeStates()
    }

    private fun initializeStates() {
        viewModelScope.launch(coroutineExceptionHandler) {
            supervisorScope {
                launch {  fetchTopHeadlines() }
                launch { fetchNewsForAllCategory() }
            }
        }
    }


    suspend fun fetchTopHeadlines() {
        fetchTopHeadlines("us").collect { newsDataState ->
            topHeadLinesState = newsDataState
            Log.d(TAG, "fetchNewsTopHeadings: ${topHeadLinesState.data}")
        }

    }


    suspend fun fetchNewsForAllCategory() {
            fetchAllCategoryNews().collect { newsDataState ->
            newsState = newsDataState
            Log.d(TAG, "fetchNewsForAllCategory: ${newsState.data}")
            newsDataState.data?.let { newsData ->
                val topNewsArticles : MutableList<Article?>? =topHeadLinesState.data?.articles
                topNewsArticles?.addAll(newsData.articles?: emptyList())
                val news = newsData.copy(
                    totalResults = topNewsArticles?.size,
                    articles = topNewsArticles
                )
                saveAllData(news)
            }
        }

    }

    private fun saveAllData(newsData: NewsData){
        viewModelScope.launch(coroutineExceptionHandler) {
            launch(Dispatchers.IO) {
                val dataSavedState=storeAllNewsData(newsData)
                dataSavedState.collect{ message->
                    Log.d(TAG, "saveAllData: $message")
                }
            }
        }
    }

    fun selectCategory(category: String){
        selectedCategory = category
        Log.d(TAG, "selectCategory: $selectedCategory")
    }


    fun getNewsBasedOnCategory(): List<Article>{
        return newsState.data?.articles?.filterNotNull()?.filter { it.category==selectedCategory} ?: emptyList()
    }


}