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
import com.example.muslim_everyday.service.AzkarNotificationService
import com.example.muslim_everyday.util.Constants
import com.example.muslim_everyday.util.Utils

class AzkarNotificationReceiver : BroadcastReceiver() {
    private var azkarNotificationManager: NotificationManager? = null
    private var builderMorningAzkar: NotificationCompat.Builder? = null
    private var builderEveningAzkar: NotificationCompat.Builder? = null

    @Suppress("NAME_SHADOWING")
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPref = context.getSharedPreferences("Notifications", Context.MODE_PRIVATE) ?: return
        val isAzkarEnabled = sharedPref.getBoolean("isAzkarEnabled?", false)

        if (isAzkarEnabled) {
            val whichAzkarTimeNow = intent.getIntExtra(Constants.PRAYER_TIME_NOW, 0)

            createAzkarNotificationChannel(context)
            startNotification(context, whichAzkarTimeNow)

            Intent(context, AzkarNotificationService::class.java).also { intent ->
                context.startService(intent)
            }
        }
    }

    private fun startNotification(context: Context, index: Int) {
        createAzkarNotificationChannel(context)

        when (index) {
            1 -> {
                morningAzkarSetNotification(context)
                morningAzkarStartNotification()
            }
            2 -> {
                eveningAzkarSetNotification(context)
                eveningAzkarStartNotification()
            }
        }
    }

    private fun createAzkarNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "AzkarReminderChannel"
            val description = "Channel Azkar"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("azkarTimings", name, importance)
            channel.description = description

            azkarNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            azkarNotificationManager?.createNotificationChannel(channel)
        } else {
            Toast.makeText(context, "Alarm failed: Azkar", Toast.LENGTH_LONG).show()
        }
    }

    // When it's Azkar time
        // Morning azkar
        private fun morningAzkarSetNotification(context: Context) {
            val intent = Intent(context, SettingsMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

            builderMorningAzkar = NotificationCompat.Builder(context, "azkarTimings")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Morning azkars")
                .setContentText("It's time to read morning azkars")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        private fun morningAzkarStartNotification() {
            azkarNotificationManager?.notify(1, builderMorningAzkar?.build())
        }

        // Evening azkar
        private fun eveningAzkarSetNotification(context: Context) {
            val intent = Intent(context, SettingsMenu::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

            builderEveningAzkar = NotificationCompat.Builder(context, "azkarTimings")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Evening azkars")
                .setContentText("It's time to read evening azkars")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
        }
        private fun eveningAzkarStartNotification() {
            azkarNotificationManager?.notify(2, builderEveningAzkar?.build())
        }

    private fun getRandomRequestCode() = Utils.getRandomInt()
}