package com.project.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.project.newsapp.data.local.entities.ArticleEntity
import com.project.newsapp.data.local.entities.NewsEntity
import com.project.newsapp.data.local.entities.SourceEntity

@Database(
    entities = [SourceEntity::class, ArticleEntity::class, NewsEntity::class],
    version = 1
)
abstract class NewsAppDatabase : RoomDatabase() {
    companion object{
        const val NEWS_DATABASE_NAME="news_app_database"
    }
    abstract fun getNewsAppDAO(): NewsAppDAO

}