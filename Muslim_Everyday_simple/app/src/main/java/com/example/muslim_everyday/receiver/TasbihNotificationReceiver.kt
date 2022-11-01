package com.example.muslim_everyday.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.muslim_everyday.R
import com.example.muslim_everyday.SettingsMenu
import com.example.muslim_everyday.service.TasbihNotificationService
import com.example.muslim_everyday.util.Constants
import com.example.muslim_everyday.util.Utils

class TasbihNotificationReceiver : BroadcastReceiver() {
    private var tasbihNotificationManager: NotificationManager? = null
    private var builderTasbih: NotificationCompat.Builder? = null

    @Suppress("NAME_SHADOWING")
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPref = context.getSharedPreferences("Notifications", Context.MODE_PRIVATE) ?: return
        val isTasbihEnabled = sharedPref.getBoolean("isTasbihEnabled?", false)

        if (isTasbihEnabled) {
            val isTasbihTime = intent.getStringExtra(Constants.PRAYER_TIME_NOW)

            if (isTasbihTime != null) {
                startNotification(context, isTasbihTime)
            }

            Intent(context, TasbihNotificationService::class.java).also { intent ->
                context.startService(intent)
            }
        }
    }

    private fun startNotification(context: Context, index: String) {
        createTasbihNotificationChannel(context)

        if (index == "Tasbih") {
            tasbihSetNotification(context)
            tasbihStartNotification()
        }
    }

    private fun createTasbihNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "TasbihReminderChannel"
            val description = "Channel Tasbih"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("tasbihTimings", name, importance)
            channel.description = description

            tasbihNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            tasbihNotificationManager?.createNotificationChannel(channel)
        } else {
            Toast.makeText(context, "Alarm failed: Tasbih", Toast.LENGTH_LONG).show()
        }
    }

    // When it's Tasbih time
        private fun tasbihSetNotification(context: Context) {
            val intent = Intent(context, SettingsMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

            builderTasbih = NotificationCompat.Builder(context, "tasbihTimings")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Tasbih time")
                .setContentText("If you have already prayed, it is time to read the tasbih")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        private fun tasbihStartNotification() {
            tasbihNotificationManager?.notify(1, builderTasbih?.build())
        }

    private fun getRandomRequestCode() = Utils.getRandomInt()
}