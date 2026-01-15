package com.project.newsapp.domain

import com.project.newsapp.domain.data.NewsData
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getTopHeadings(countryCode: String): NewsData?

    suspend fun getNewsOnQuery(query: String): NewsData?

    suspend fun getNewsOnCategory(category: String): NewsData?

}