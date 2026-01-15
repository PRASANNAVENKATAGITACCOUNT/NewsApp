package com.project.newsapp.data.remote.dto

data class NewsDataDto(
    val articles: List<ArticleDto?>?,
    val status: String?,
    val totalResults: Int?,
    val code: String?=null,
    val message : String?= null
)