package com.project.newsapp.data

import androidx.room.ColumnInfo
import com.project.newsapp.data.local.entities.ArticleEntity
import com.project.newsapp.data.local.entities.NewsEntity
import com.project.newsapp.data.local.entities.SourceEntity
import com.project.newsapp.data.remote.dto.ArticleDto
import com.project.newsapp.data.remote.dto.NewsDataDto
import com.project.newsapp.data.remote.dto.SourceDto
import com.project.newsapp.domain.data.Article
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.domain.data.Source

fun SourceDto.toSource(): Source{
    return Source(
        id= this.id,
        name=this.name
    )
}

fun ArticleDto.toArticle(): Article{
    return Article(
       author=this.author,
        content=this.content,
        description=this.description,
        publishedAt=this.publishedAt,
        source=this.source?.toSource(),
        title=this.title,
        url=this.url,
        urlToImage = this.urlToImage
    )
}

fun NewsDataDto.toNewsData(): NewsData{
    return NewsData(
        articles = this.articles?.map { it-> it?.toArticle() }?.toMutableList(),
        status=this.status,
        totalResults=this.totalResults,
        code=this.code,
        message = this.message
    )
}

fun ArticleEntity.toArticle(): Article{

    return Article(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        category = this.category,
        title = this.title,
        url=this.url,
        urlToImage = this.urlToImage
    )
}

fun Article.toArticleEntity() : ArticleEntity {
    return ArticleEntity(
        author = this.author,
        content = this.content,
        description = this.description,
        publishedAt = this.publishedAt,
        category = this.category,
        title = this.title,
        url=this.url,
        urlToImage = this.urlToImage
    )
}

fun NewsData.toNewsEntity(): NewsEntity{
    return  NewsEntity(
        status = this.status,
        totalResults = this.totalResults,
        code = this.code,
        message = this.message
    )
}

fun Source.toSourceEntity(): SourceEntity{
    return SourceEntity(
        name = " ${this.name}"
    )
}

