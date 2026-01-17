package com.project.newsapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newsapp.domain.data.NewsDataState
import com.project.newsapp.domain.data.NewsQueryState
import com.project.newsapp.domain.network.NetworkConnection
import com.project.newsapp.domain.network.NetworkConnectivityObserver
import com.project.newsapp.domain.usecase.FetchNewsOnQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val fetchNewsOnQuery: FetchNewsOnQuery,
    private val networkConnectivityObserver: NetworkConnection
) : ViewModel() {


    var newsQueryState by mutableStateOf(NewsQueryState())
        private set

    init {
        initializeState()
    }
    private fun initializeState() {
        viewModelScope.launch {
            networkConnectivityObserver.isConnected().collect { isConnected->
                if(!isConnected){
                    newsQueryState = NewsQueryState(loading = false, data = null,error="No Network")
                }
            }
        }
    }



    fun fetchNews(query: String){
        viewModelScope.launch {
            networkConnectivityObserver.isConnected().collect { isConnected->
                if(isConnected){
                    val newsQueryStateFlow=fetchNewsOnQuery(query)
                    newsQueryStateFlow.collect {newsStateData->
                        newsQueryState = NewsQueryState(
                            loading = newsStateData.loading,
                            error= newsStateData.error,
                            data = newsStateData.data)
                    }
                }else{
                    newsQueryState = NewsQueryState(loading = false, data = null, error = "Error : No Network.")
                }
            }
        }
    }

}