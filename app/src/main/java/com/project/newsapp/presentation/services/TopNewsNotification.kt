package com.project.newsapp.presentation.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.project.newsapp.domain.data.Article

class TopNewsNotification : Service() {
    override fun onBind(p0: Intent?): IBinder? {
        return null
    }
}