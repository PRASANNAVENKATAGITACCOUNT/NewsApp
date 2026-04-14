package com.project.newsapp.presentation.screens.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.navigation.compose.rememberNavController
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.project.newsapp.presentation.screens.auth.AuthScreen
import com.project.newsapp.presentation.screens.home_screen.HomeScreen
import com.project.newsapp.presentation.services.TopNewsNotificationWorker
import com.project.newsapp.presentation.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    companion object{
        const val UNIQUE_WORK_NAME="top_news_notification"
    }

    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.entries.all { it.value }

        if (allGranted) {
            scheduleTopNewsWorker()
        } else {
            permissions.forEach { (permission, isGranted) ->
                when {
                    isGranted -> {

                    }
                    !isGranted -> {

                        //handleDeniedPermission(permission)
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                requestRequiredPermission()
                MainScreen()
            }
        }
    }


    fun requestRequiredPermission() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.POST_NOTIFICATIONS
        )

        val missingPermissions = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }.toTypedArray()
        if (missingPermissions.isNotEmpty()) {
            requestPermissionLauncher.launch(permissions)
        }
    }

    private fun scheduleTopNewsWorker() {
        val topNewsWorkerConstraint = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val topNewsWorkRequest =
            PeriodicWorkRequestBuilder<TopNewsNotificationWorker>(30, TimeUnit.MINUTES)
                .setConstraints(topNewsWorkerConstraint)
                .addTag(TopNewsNotificationWorker.Companion.TAG)
                .build()

        WorkManager.Companion.getInstance(this).enqueueUniquePeriodicWork(
            UNIQUE_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            topNewsWorkRequest
        )
        WorkManager.Companion.getInstance(this)
            .getWorkInfosByTagLiveData(TopNewsNotificationWorker.Companion.TAG)
            .observe(ProcessLifecycleOwner.Companion.get()) { workInfos ->
                workInfos.forEach {
                    Log.d(TopNewsNotificationWorker.Companion.TAG, it.state.name)
                }
            }
    }


}