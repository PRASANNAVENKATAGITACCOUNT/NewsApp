package com.project.newsapp.domain.data

import java.io.Serializable


data class NewsData(
    val articles: MutableList<Article?>?= mutableListOf(),
    val status: String?="",
    val totalResults: Int?=0,
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
    var category: String="",
    val urlToImage: String?=null
): Serializable {

}

data class Source(
    val id: String?,
    val name: String?
): Serializable{

}