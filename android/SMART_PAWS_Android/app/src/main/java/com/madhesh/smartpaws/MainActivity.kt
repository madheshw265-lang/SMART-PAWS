package com.madhesh.smartpaws

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: DatabaseReference
    private var listener: ValueEventListener? = null
    private lateinit var loginPanel: LinearLayout
    private lateinit var dashboardPanel: LinearLayout
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var loginMessage: TextView
    private lateinit var statusCard: TextView
    private lateinit var alertText: TextView
    private lateinit var distanceText: TextView
    private lateinit var gpsText: TextView
    private lateinit var satelliteText: TextView
    private lateinit var homeText: TextView
    private lateinit var radiusText: TextView
    private lateinit var updatedText: TextView
    private lateinit var mapButton: Button
    private lateinit var logoutButton: Button
    private var currentLat: Double? = null
    private var currentLng: Double? = null

    private val notificationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        db = FirebaseDatabase.getInstance().getReference("smartPaws/device01")
        bindViews()
        NotificationHelper.createChannel(this)
        requestNotificationPermission()
        loginButton.setOnClickListener { login() }
        logoutButton.setOnClickListener { logout() }
        mapButton.setOnClickListener { openMap() }
        if (auth.currentUser != null) { showDashboard(); startMonitor(); listenForDevice() } else showLogin()
    }

    private fun bindViews() {
        loginPanel = findViewById(R.id.loginPanel); dashboardPanel = findViewById(R.id.dashboardPanel)
        emailInput = findViewById(R.id.emailInput); passwordInput = findViewById(R.id.passwordInput)
        loginButton = findViewById(R.id.loginButton); loginMessage = findViewById(R.id.loginMessage)
        statusCard = findViewById(R.id.statusCard); alertText = findViewById(R.id.alertText)
        distanceText = findViewById(R.id.distanceText); gpsText = findViewById(R.id.gpsText)
        satelliteText = findViewById(R.id.satelliteText); homeText = findViewById(R.id.homeText)
        radiusText = findViewById(R.id.radiusText); updatedText = findViewById(R.id.updatedText)
        mapButton = findViewById(R.id.mapButton); logoutButton = findViewById(R.id.logoutButton)
    }

    private fun login() {
        val email = emailInput.text.toString().trim(); val password = passwordInput.text.toString()
        if (email.isEmpty() || password.isEmpty()) { loginMessage.text = "Enter your Firebase email and password."; return }
        loginButton.isEnabled = false; loginMessage.text = "Connecting..."
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            loginButton.isEnabled = true
            if (task.isSuccessful) { loginMessage.text = ""; showDashboard(); startMonitor(); listenForDevice() }
            else loginMessage.text = task.exception?.localizedMessage ?: "Login failed."
        }
    }

    private fun showLogin() { loginPanel.visibility = LinearLayout.VISIBLE; dashboardPanel.visibility = LinearLayout.GONE }
    private fun showDashboard() { loginPanel.visibility = LinearLayout.GONE; dashboardPanel.visibility = LinearLayout.VISIBLE }
    private fun startMonitor() { ContextCompat.startForegroundService(this, Intent(this, MonitorService::class.java)) }

    private fun listenForDevice() {
        listener?.let { db.removeEventListener(it) }
        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lat = snapshot.child("latitude").getValue(Double::class.java)
                val lng = snapshot.child("longitude").getValue(Double::class.java)
                val distance = snapshot.child("distance").getValue(Double::class.java)
                val homeLat = snapshot.child("homeLatitude").getValue(Double::class.java)
                val homeLng = snapshot.child("homeLongitude").getValue(Double::class.java)
                val radius = snapshot.child("radius").getValue(Double::class.java) ?: 10.0
                val sats = snapshot.child("satellites").getValue(Long::class.java) ?: 0L
                val status = snapshot.child("status").getValue(String::class.java) ?: "UNKNOWN"
                currentLat = lat; currentLng = lng
                if (status.equals("OUTSIDE", true)) {
                    statusCard.text = "🔴 OUTSIDE"; statusCard.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.paw_red)); statusCard.setBackgroundColor(0xFFFFE8E8.toInt()); alertText.text = "⚠️ Your dog has left the safe zone!"
                } else if (status.equals("SAFE", true)) {
                    statusCard.text = "🟢 SAFE"; statusCard.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.paw_green)); statusCard.setBackgroundColor(0xFFE8F7EE.toInt()); alertText.text = "Your dog is inside the safe zone."
                } else { statusCard.text = "⚪ $status"; alertText.text = "Waiting for a valid GPS/home status." }
                distanceText.text = "Distance: ${distance?.let { String.format("%.2f m", it) } ?: "--"}"
                gpsText.text = "GPS: ${lat?.let { String.format("%.6f", it) } ?: "--"}, ${lng?.let { String.format("%.6f", it) } ?: "--"}"
                satelliteText.text = "Satellites: $sats"
                homeText.text = "Home: ${homeLat?.let { String.format("%.6f", it) } ?: "--"}, ${homeLng?.let { String.format("%.6f", it) } ?: "--"}"
                radiusText.text = "Safe radius: ${String.format("%.1f m", radius)}"
                updatedText.text = "Status: $status • Live Firebase data"
            }
            override fun onCancelled(error: DatabaseError) { updatedText.text = "Firebase error: ${error.message}" }
        }
        db.addValueEventListener(listener!!)
    }

    private fun openMap() {
        val lat = currentLat ?: return; val lng = currentLng ?: return
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("geo:$lat,$lng?q=$lat,$lng(SMART%20PAWS%20Dog)")))
    }

    private fun logout() { listener?.let { db.removeEventListener(it) }; stopService(Intent(this, MonitorService::class.java)); auth.signOut(); showLogin(); Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show() }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    override fun onDestroy() { listener?.let { db.removeEventListener(it) }; super.onDestroy() }
}
