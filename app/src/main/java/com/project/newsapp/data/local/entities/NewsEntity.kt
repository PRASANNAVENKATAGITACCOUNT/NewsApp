package com.project.newsapp.data.local.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class NewsEntity(
    @PrimaryKey(autoGenerate = true)
    val newsId: Long=0,
    val status: String?,
    val totalResults: Int?,
    val code: String?=null,
    val message : String?= null
)


data class NewsArticles(
    @Embedded val newsEntity: NewsEntity,
    @Relation(
        parentColumn = "newsId",
        entityColumn = "articleId"
    )
    val articles: List<ArticleEntity>
)
