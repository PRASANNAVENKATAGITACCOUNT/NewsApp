package com.project.newsapp.data

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
        articles = this.articles?.map { it-> it?.toArticle() },
        status=this.status,
        totalResults=this.totalResults,
        code=this.code,
        message = this.message
    )
}

