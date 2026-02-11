package com.project.newsapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.project.newsapp.data.local.entities.ArticleEntity
import com.project.newsapp.data.local.entities.ArticleSource
import com.project.newsapp.data.local.entities.NewsArticles
import com.project.newsapp.data.local.entities.NewsEntity
import com.project.newsapp.data.local.entities.SourceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsAppDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllArticle(articleEntityList: List<ArticleEntity>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsEntity(newsEntity: NewsEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSourceEntity(sourceEntityList: List<SourceEntity>)

    @Query("SELECT * FROM ARTICLEENTITY")
    fun getAllArticles(): List<ArticleEntity>

    @Delete
    suspend fun deleteNewsEntity(newsEntity: NewsEntity)

    @Delete
    suspend fun deleteArticleEntity(articleEntity: ArticleEntity)

    @Delete
    suspend fun deleteSourceEntity(sourceEntity: SourceEntity)
//
//
//    @Transaction
//    @Query("SELECT * FROM ARTICLEENTITY")
//    suspend fun getArticleSource():List<ArticleSource>
//
//
//    @Transaction
//    @Query("SELECT * FROM NEWSENTITY")
//    suspend fun getNewsArticlesList(): List<NewsArticles>



}