package com.madhesh.smartpaws

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class SmartPawsMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) { super.onNewToken(token) }
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title ?: message.data["title"] ?: "SMART PAWS"
        val body = message.notification?.body ?: message.data["body"] ?: "New SMART PAWS alert."
        NotificationHelper.showAlert(this, title, body)
    }
}
