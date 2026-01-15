package com.project.newsapp.data.remote

import com.project.newsapp.BuildConfig
import com.project.newsapp.data.remote.dto.NewsDataDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsRESTAPI {

    companion object{
        const val BASE_URL="https://newsapi.org/v2/"
        //Base URL: top-headlines?country=us&apiKey=$API_KEY
    }

    @GET("top-headlines")
    suspend fun getTopHeadLines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY
    ): Response<NewsDataDto>

    @GET("everything")
    suspend fun getNewsOnQuery(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String= BuildConfig.NEWS_API_KEY
    ): Response<NewsDataDto>


    @GET("top-headlines")
    suspend fun getNewsOnCategory(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String= BuildConfig.NEWS_API_KEY
    ): Response<NewsDataDto>

}