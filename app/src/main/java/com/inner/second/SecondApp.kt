package com.inner.second

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SecondApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val channel = NotificationChannel(
            "ContractDueDateReminder",
            "계약서 완성 만료 알림",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}