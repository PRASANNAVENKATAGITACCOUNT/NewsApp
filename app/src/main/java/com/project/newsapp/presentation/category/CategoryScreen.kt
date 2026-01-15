package com.project.newsapp.presentation.category

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.newsapp.domain.data.Article
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.domain.data.NewsDataState
import com.project.newsapp.presentation.components.ArticleComponent
import com.project.newsapp.ui.theme.NewsAppTheme


@Composable
fun CategoryScreen(state: NewsDataState) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            state.loading->{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    CircularProgressIndicator()
                }
            }
            state.error!=null->{
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(state.error)
                }
            }
            state.data?.articles != null -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp, start = 8.dp, end = 8.dp, bottom = 8.dp),
                ) {
                    val data: List<Article> = state.data.articles.filterNotNull()
                    items(data) { article ->
                        ArticleComponent(article)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    val newsData = NewsData(
        status = "OK",
        totalResults = 35,
        code = "",
        message = null,
        articles = listOf(
            Article(
                title="Article Title",
                description = "Description", author = "New Author", publishedAt = "15-01-2026"
            ),
            Article(
                title="Article Title",
                description = "Description", author = "New Author", publishedAt = "15-01-2026"
            ),
            Article(
                title="Article Title",
                description = "Description", author = "New Author", publishedAt = "15-01-2026"
            ),
            Article(
                title="Article Title",
                description = "Description", author = "New Author", publishedAt = "15-01-2026"
            )
        )
    )
    NewsAppTheme {
        CategoryScreen(
            state = NewsDataState(loading = false, data = newsData, error = null)
        )
    }
}