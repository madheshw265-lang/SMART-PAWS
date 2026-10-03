package com.madhesh.smartpaws

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.firebase.database.*

class MonitorService : Service() {
    private lateinit var db: DatabaseReference
    private var listener: ValueEventListener? = null

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannel(this)
        startForeground(NotificationHelper.SERVICE_NOTIFICATION_ID, NotificationHelper.serviceNotification(this))

        val prefs = getSharedPreferences("smart_paws", MODE_PRIVATE)
        var lastStatus = prefs.getString("last_status", null)
        db = FirebaseDatabase.getInstance().getReference("smartPaws/device01")

        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status = snapshot.child("status").getValue(String::class.java) ?: return
                val normalized = status.uppercase()
                if (lastStatus != null && normalized != lastStatus) {
                    when (normalized) {
                        "OUTSIDE" -> NotificationHelper.showAlert(this@MonitorService, "⚠️ SMART PAWS ALERT", "Your dog has left the 10 m safe zone.")
                        "SAFE" -> NotificationHelper.showAlert(this@MonitorService, "✅ SMART PAWS SAFE", "Your dog has returned to the safe zone.")
                    }
                }
                lastStatus = normalized
                prefs.edit().putString("last_status", normalized).apply()
            }
            override fun onCancelled(error: DatabaseError) { }
        }
        db.addValueEventListener(listener!!)
    }

    override fun onDestroy() {
        listener?.let { db.removeEventListener(it) }
        super.onDestroy()
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
