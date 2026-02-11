package com.project.newsapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class NewsApplication: Application() {

    companion object{
        const val CHANNEL_ID="NEWS_NOTIFICATION_CHANNEL_ID"
    }

    override fun onCreate() {
        super.onCreate()
        //createNotificationChannel()
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.news_notification_channel)
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