package com.project.newsapp.presentation.screens.article_screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.project.newsapp.domain.data.Article
import com.project.newsapp.presentation.ui.theme.NewsAppTheme

class ArticleScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val article = intent?.extras?.getSerializable("article", Article::class.java)
                article?.let {
                    ArticleScreen(it) {
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        }
    }
}