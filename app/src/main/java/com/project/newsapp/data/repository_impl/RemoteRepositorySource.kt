package com.project.newsapp.data.repository_impl

import com.project.newsapp.data.remote.NewsRESTAPI
import com.project.newsapp.data.toNewsData
import com.project.newsapp.domain.Repository
import com.project.newsapp.domain.data.NewsData


class RemoteRepositorySource(val newsAPI: NewsRESTAPI): Repository {
    override suspend fun getTopHeadings(countryCode: String): NewsData? {
        return newsAPI.getTopHeadLines(country = countryCode).body()?.toNewsData()
    }

    override suspend fun getNewsOnQuery(query: String): NewsData? {
        return newsAPI.getNewsOnQuery(query=query).body()?.toNewsData()
    }

    override suspend fun getNewsOnCategory(category: String): NewsData? {
        return newsAPI.getNewsOnCategory(category=category).body()?.toNewsData()
    }

}