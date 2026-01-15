package com.project.newsapp.domain.data


data class NewsData(
    val articles: List<Article?>?,
    val status: String?,
    val totalResults: Int?,
    val code: String?=null,
    val message : String?= null
)

data class Article(
    val author: String?=null,
    val content: String?=null,
    val description: String?=null,
    val publishedAt: String?=null,
    val source: Source?=null,
    val title: String?=null,
    val url: String?=null,
    val urlToImage: String?=null
)

data class Source(
    val id: String?,
    val name: String?
)