package com.project.newsapp.presentation.screens.home_screen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.domain.usecase.FetchNewsOnQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.dec

@HiltViewModel
class SelectedTopicViewModel @Inject constructor(
    val fetchNewsOnQuery: FetchNewsOnQuery
) : ViewModel() {

    val recentlyFollowedItems = getFollowingTopics()
    var selectedTopic  = mutableStateOf(recentlyFollowedItems[0])

    val map = mutableMapOf<String, NewsData>()
    private var _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading

    init {
        fetchNewsOnTopic()
    }



    fun fetchNewsOnTopic() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {

                val tasks = recentlyFollowedItems.map { item ->
                    async (Dispatchers.IO) {
                        val result = fetchNewsOnQuery(item.followingTopic)
                            .filter { it.data != null || it.error != null }
                            .first()
                        item.followingTopic to result.data
                    }
                }
                val results = tasks.awaitAll()
                results.forEach { (topic, data) ->
                    if (data != null) {
                        map[topic] = data
                    }
                }
            } catch (e: Exception) {
                Log.d("kmnbjjjjjjj", "fetchNewsOnTopic: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }






}