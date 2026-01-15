package com.project.newsapp.di

import com.project.newsapp.BuildConfig
import com.project.newsapp.data.remote.NewsRESTAPI
import com.project.newsapp.data.repository_impl.RemoteRepositorySource
import com.project.newsapp.domain.Repository
import com.project.newsapp.domain.usecase.FetchTopHeadlines
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.level = HttpLoggingInterceptor.Level.BODY
        } else {
            logging.level = HttpLoggingInterceptor.Level.NONE
        }
        return OkHttpClient.Builder()
            // Add a timeout to prevent hanging
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }


    @Provides
    @Singleton
    fun providesRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(NewsRESTAPI.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Provides
    @Singleton
    fun providesNewsRESTAPIInstance(retrofit: Retrofit): NewsRESTAPI {
        return retrofit.create(NewsRESTAPI::class.java)
    }


    @Provides
    @Singleton
    fun  providesRemoteRepositoryImpl(newsRESTAPI: NewsRESTAPI): RemoteRepositorySource{
        return RemoteRepositorySource(newsRESTAPI)
    }





}