package com.project.newsapp.domain.data

data class NewsQueryState(
    val loading: Boolean = false,
    val data: NewsData?= null,
    val error: String?=null,
    val newsQueryText: String=""
)