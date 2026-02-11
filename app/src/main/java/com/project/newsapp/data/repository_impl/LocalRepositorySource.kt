package com.project.newsapp.data.repository_impl

import android.util.Log
import com.project.newsapp.data.local.NewsAppDatabase
import com.project.newsapp.data.local.entities.ArticleEntity
import com.project.newsapp.data.local.entities.SourceEntity
import com.project.newsapp.data.toArticle
import com.project.newsapp.data.toArticleEntity
import com.project.newsapp.data.toNewsEntity
import com.project.newsapp.data.toSourceEntity
import com.project.newsapp.domain.Repository
import com.project.newsapp.domain.data.Article
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.presentation.constants.NEWS_TOP_HEADLINES
import javax.inject.Inject

class LocalRepositorySource @Inject constructor(
    val database: NewsAppDatabase
) : Repository{

    val TAG = "LOCAL_REPOSITORY_SOURCE"

    override suspend fun getTopHeadings(countryCode: String): NewsData? {
       val newsDAO= database.getNewsAppDAO()
        val allArticles =  newsDAO.getAllArticles()
        val topHeadings : MutableList<Article?> = allArticles.map { it-> it.toArticle() }.filter { it.category == NEWS_TOP_HEADLINES }.toMutableList()
        Log.d(TAG, "getNewsTopHeading: $topHeadings")
        val newsData= NewsData(
            status = "OK",
            totalResults = topHeadings.size,
            articles = topHeadings
        )
        return newsData
    }

    override suspend fun getNewsOnQuery(query: String): NewsData? {
        val newsDAO= database.getNewsAppDAO()
        val allArticles =  newsDAO.getAllArticles();
        val articlesOnQuery : MutableList<Article?>? = allArticles
            .map { it.toArticle() }
            .filter { it-> (it.title==query ||
                    it.category==query ||
                    it.content?.contains(query)==true ||
                    it.description?.contains(query)==true) }
            .toMutableList()
        Log.d(TAG, "getNewsOnQuery: $articlesOnQuery")
        val newsData= NewsData(
            status = "OK",
            totalResults = articlesOnQuery?.size,
            articles = articlesOnQuery
        )
        return newsData
    }

    override suspend fun getNewsOnCategory(category: String): NewsData? {
        val newsDAO= database.getNewsAppDAO()
        val allArticles =  newsDAO.getAllArticles()
        val articlesOnCategory : MutableList<Article?> = allArticles.map { it.toArticle() }.filter { it.category == category }.toMutableList()
        Log.d(TAG, "getNewsOnCategory: $category $articlesOnCategory")
        val newsData= NewsData(
            status = "OK",
            totalResults = articlesOnCategory.size,
            articles = articlesOnCategory
        )
        return newsData
    }

    override suspend fun getNewsFromAllCategory(): NewsData? {
        val newsDAO= database.getNewsAppDAO()
        val allArticles : MutableList<Article?> =  newsDAO.getAllArticles().map { it-> it.toArticle() }.toMutableList()
        Log.d(TAG, "getNewsOnAllCategory: $allArticles")
        val newsData= NewsData(
            status = "OK",
            totalResults = allArticles.size,
            articles = allArticles.toMutableList()
        )
        return newsData
    }

    suspend fun storeNewsDataLocally(data: NewsData) : String{
        try {
            val articleList = data.articles?.filterNotNull()
            val sourceEntityList : MutableList<SourceEntity> = mutableListOf()
            articleList?.forEach { article->
                article.source?.let { articleSource->
                    sourceEntityList.add(articleSource.toSourceEntity())
                }
            }
            val articleEntityList : List<ArticleEntity> = articleList?.map { it-> it.toArticleEntity() } ?: emptyList<ArticleEntity>()
            val newsEntity = data.toNewsEntity()
            val newsAppDAO = database.getNewsAppDAO()
            newsAppDAO.insertSourceEntity(sourceEntityList)
            newsAppDAO.insertAllArticle(articleEntityList)
            newsAppDAO.insertNewsEntity(newsEntity)
            return "Successfully Saved"
        }catch (e: Exception){
            Log.d(TAG, "storeNewsDataLocally: ${e.message}")
            return "Error: ${e.message}"
        }
    }
}