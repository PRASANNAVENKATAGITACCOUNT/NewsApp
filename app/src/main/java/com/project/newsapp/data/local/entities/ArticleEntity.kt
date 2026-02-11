package com.project.newsapp.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation


@Entity
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true)
    val articleId: Long=0L,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    @ColumnInfo(defaultValue = "")
    val category: String,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

@Entity
data class ArticleSource(
    @Embedded val articleEntity: ArticleEntity,
    @Relation(
        parentColumn = "articleId",
        entityColumn = "sourceId"
    )
    val sourceEntity: SourceEntity
)



