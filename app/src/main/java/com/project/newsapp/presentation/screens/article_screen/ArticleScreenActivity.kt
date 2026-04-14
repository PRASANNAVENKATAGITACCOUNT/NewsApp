package com.project.newsapp.presentation.screens.article_screen

import android.os.Build
import android.os.Bundle
import android.view.WindowInsetsController
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import com.project.newsapp.R
import com.project.newsapp.domain.data.Article
import com.project.newsapp.presentation.ui.theme.NewsAppTheme

class ArticleScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                lightScrim = android.graphics.Color.TRANSPARENT,
                darkScrim = android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            NewsAppTheme {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
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
}