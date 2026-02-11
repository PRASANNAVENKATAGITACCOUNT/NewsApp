package com.project.newsapp.data.repository_impl

import android.util.Log
import androidx.compose.foundation.interaction.HoverInteraction
import com.project.newsapp.data.remote.NewsRESTAPI
import com.project.newsapp.data.toArticle
import com.project.newsapp.data.toNewsData
import com.project.newsapp.domain.Repository
import com.project.newsapp.domain.data.Article
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.presentation.constants.NEWSAPP_CATEGORY_PATH
import com.project.newsapp.presentation.constants.NEWS_TOP_HEADLINES
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch


class RemoteRepositorySource(val newsAPI: NewsRESTAPI): Repository {

    companion object{
        const val TAG="RemoteRepositorySource"
    }
    override suspend fun getTopHeadings(countryCode: String): NewsData? {
        var newsData=  newsAPI.getTopHeadLines(country = countryCode).body()?.toNewsData()
        newsData = setCategoryForArticle(newsData, NEWS_TOP_HEADLINES)
        return newsData
    }

    override suspend fun getNewsOnQuery(query: String): NewsData? {
        return newsAPI.getNewsOnQuery(query=query).body()?.toNewsData()
    }

    override suspend fun getNewsOnCategory(category: String): NewsData? {
        var newsData= newsAPI.getNewsOnCategory(category=category).body()?.toNewsData()
        newsData = setCategoryForArticle(newsData,category)
        return newsData
    }

    override suspend fun getNewsFromAllCategory(): NewsData? =  coroutineScope {

        val requests = NEWSAPP_CATEGORY_PATH.getAllCategories().map { category ->
            async {
                category to newsAPI.getNewsOnCategory(category.route)
            }
        }

        val responses = requests.mapNotNull { deferred ->
            try {
                deferred.await()
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching category", e)
                null
            }
        }

        NewsData(
            status = if (responses.all { it.second.body()?.status == "ok" }) "ok" else "partial",
            totalResults = responses.sumOf { it.second.body()?.totalResults ?: 0 },
            articles = responses.flatMap { (category, response) ->
                response.body()?.articles
                    ?.map { it?.toArticle()?.copy(category = category.route) }
                    ?: emptyList()
            }.toMutableList(),
            message = responses
                .mapNotNull { (category, response) ->
                    response.body()?.message?.let {
                        "${category.route}: $it"
                    }
                }.joinToString(" | ")
        )
    }

    private fun setCategoryForArticle(newsData: NewsData?, categoryText: String) : NewsData?{
        newsData?.articles?.forEach { article ->
            article?.category = categoryText
        }
        return newsData
    }

}