package com.madhesh.smartpaws

import android.app.*
import android.content.Context
import androidx.core.app.NotificationCompat

object NotificationHelper {
    const val CHANNEL_ID = "smart_paws_alerts"
    const val SERVICE_NOTIFICATION_ID = 1001
    const val ALERT_NOTIFICATION_ID = 1002

    fun createChannel(context: Context) {
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(NotificationChannel(CHANNEL_ID, "SMART PAWS Alerts", NotificationManager.IMPORTANCE_HIGH).apply {
            description = "Dog safe-zone alerts"
            enableVibration(true)
        })
    }

    fun serviceNotification(context: Context): Notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_notification)
        .setContentTitle("SMART PAWS monitoring")
        .setContentText("Monitoring your dog's Firebase location")
        .setOngoing(true)
        .setCategory(NotificationCompat.CATEGORY_SERVICE)
        .setPriority(NotificationCompat.PRIORITY_LOW)
        .build()

    fun showAlert(context: Context, title: String, message: String) {
        createChannel(context)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()
        context.getSystemService(NotificationManager::class.java).notify(ALERT_NOTIFICATION_ID, notification)
    }
}
