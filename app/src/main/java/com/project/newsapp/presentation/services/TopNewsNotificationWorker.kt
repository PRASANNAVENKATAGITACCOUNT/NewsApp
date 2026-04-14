package com.project.newsapp.presentation.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.project.newsapp.NewsApplication.Companion.CHANNEL_ID
import com.project.newsapp.R
import com.project.newsapp.data.remote.NewsRESTAPI
import com.project.newsapp.data.remote.dto.ArticleDto
import com.project.newsapp.data.repository_impl.RemoteRepositorySource
import com.project.newsapp.data.toArticle
import com.project.newsapp.domain.data.Article
import com.project.newsapp.domain.data.NewsData
import com.project.newsapp.presentation.screens.article_screen.ArticleScreenActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import okio.IOException

@HiltWorker
class TopNewsNotificationWorker  @AssistedInject constructor(
    @Assisted val appContext: Context,
    @Assisted val workerParams: WorkerParameters,
    val remoteRepositorySource : RemoteRepositorySource
) : CoroutineWorker(appContext,workerParams) {


    companion object{
        const val TAG="TopNewsWorker:"
        const val REQUEST_NEWS_NOTIFICATION=1001

        const val NOTIFICATION_ID=1234666754
    }

    override suspend fun doWork(): Result{
        try{
            Log.d(TAG, " Starting Notification Worker ")
            var firstArticle = Article(
                title = " First News Article"
            )
            val newsData = remoteRepositorySource.getTopHeadings("us")
            val articles=newsData?.articles?.filterNotNull() ?: emptyList()
            if(articles.isNotEmpty()){
                firstArticle =  articles[0]
                Log.d(TAG, "Top news notification shown successfully")
            }
            createNotification(firstArticle)
            Log.d(TAG, "Top news notification shown successfully")
            return Result.success()
        }catch (e: IOException){
            Log.d(TAG, "doWork: IO Error ${e.message}")
            return Result.retry()
        } catch (e: Exception){
            Log.d(TAG, "doWork: Exception ${e.message}")
            return Result.failure()
        }

    }



    fun createNotification(article: Article){
        val intent = Intent(appContext, ArticleScreenActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(appContext, REQUEST_NEWS_NOTIFICATION,
            intent, PendingIntent.FLAG_IMMUTABLE)
        // .setContentIntent(pendingIntent)

        val builder = NotificationCompat.Builder(appContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.news_app_icon)
            .setContentTitle("Top News")
            .setContentText(article.title?:"News is there Please check")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        showNotification(builder)
    }

    fun showNotification(builder: NotificationCompat.Builder){
        with(NotificationManagerCompat.from(appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                // ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                // public fun onRequestPermissionsResult(requestCode: Int, permissions: Array&lt;out String&gt;,
                //                                        grantResults: IntArray)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.

                return@with
            }
            // notificationId is a unique int for each notification that you must define.

            notify(NOTIFICATION_ID, builder.build())
        }
    }


}