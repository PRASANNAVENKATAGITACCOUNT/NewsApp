package com.project.newsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newsapp.domain.data.NewsQueryState
import com.project.newsapp.presentation.screens.SearchNewsScreen
import com.project.newsapp.presentation.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchNewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val searchViewModel = hiltViewModel<SearchNewsViewModel>()
                SearchNewsScreen(
                    searchViewModel.newsQueryState,
                    onBackPress = {
                        onBackPressedDispatcher.onBackPressed()
                    }
                ){ newsSearchText->
                    searchViewModel.fetchNews(newsSearchText)
                }
            }
        }
    }
}
