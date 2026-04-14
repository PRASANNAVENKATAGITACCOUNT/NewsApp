package com.project.newsapp.data.remote.dto

import retrofit2.http.GET

data class NewsDataDto(
    val articles: List<ArticleDto?>?=null,
    val status: String?="",
    val totalResults: Int?=0,
    val code: String?=null,
    val message : String?= null
)