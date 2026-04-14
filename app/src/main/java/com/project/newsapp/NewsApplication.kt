package com.project.newsapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.project.newsapp.presentation.services.TopNewsNotificationWorker
import dagger.hilt.android.HiltAndroidApp
import jakarta.inject.Inject
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class NewsApplication : Application(), Configuration.Provider {

    companion object{
        const val CHANNEL_ID="NEWS_NOTIFICATION_CHANNEL_ID"
    }
    @Inject lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .setMinimumLoggingLevel(android.util.Log.DEBUG)
            .build()

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.news_notification_name)
            val descriptionTextData = getString(R.string.news_channel_description)
            val channel = NotificationChannel(CHANNEL_ID, name,
                NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = descriptionTextData
            }
            val notificationManager : NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}