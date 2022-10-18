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
import com.example.muslim_everyday.service.NotificationService
import com.example.muslim_everyday.util.Constants
import com.example.muslim_everyday.util.Utils

class NotificationReceiver : BroadcastReceiver() {
    private var notificationManager: NotificationManager? = null
    private var builderFajr: NotificationCompat.Builder? = null
    private var builderDhuhr: NotificationCompat.Builder? = null
    private var builderAsr: NotificationCompat.Builder? = null
    private var builderMaghrib: NotificationCompat.Builder? = null
    private var builderIsha: NotificationCompat.Builder? = null

    @Suppress("NAME_SHADOWING")
    override fun onReceive(context: Context, intent: Intent) {
        val sharedPref = context.getSharedPreferences("Notifications", Context.MODE_PRIVATE) ?: return
        val isNotificationEnabled = sharedPref.getBoolean("isNotificationEnabled", false)

        if (isNotificationEnabled) {
            val whichPrayerTimeNow = intent.getIntExtra(Constants.PRAYER_TIME_NOW, 0)
            startNotification(context, whichPrayerTimeNow)

            Intent(context, NotificationService::class.java).also { intent ->
                context.startService(intent)
            }
        }
    }

    private fun startNotification(context: Context, index: Int) {
        createNotificationChannel(context)

        when (index) {
            1 -> {
                fajrSetNotification(context)
                fajrStartNotification()
            }
            2 -> {
                dhuhrSetNotification(context)
                dhuhrStartNotification()
            }
            3 -> {
                asrSetNotification(context)
                asrStartNotification()
            }
            4 -> {
                maghribSetNotification(context)
                maghribStartNotification()
            }
            5 -> {
                ishaSetNotification(context)
                ishaStartNotification()
            }
        }
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "NamazReminderChannel"
            val description = "Channel Namaz"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("namazTimings", name, importance)
            channel.description = description

            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        } else {
            Toast.makeText(context, "Alarm failed", Toast.LENGTH_LONG).show()
        }
    }

    // Times of Namaz methods
        // Fajr
            private fun fajrSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderFajr = NotificationCompat.Builder(context, "namazTimings")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Fajr")
                    .setContentText("Content Text - Fajr")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            private fun fajrStartNotification() {
                notificationManager?.notify(1, builderFajr?.build())
            }
            // Dhuhr
            private fun dhuhrSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderDhuhr = NotificationCompat.Builder(context, "namazTimings")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Dhuhr")
                    .setContentText("Content Text - Dhuhr")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            private fun dhuhrStartNotification() {
                notificationManager?.notify(2, builderDhuhr?.build())
            }
            // Asr
            private fun asrSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderAsr = NotificationCompat.Builder(context, "namazTimings")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Asr")
                    .setContentText("Content Text - Asr")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            private fun asrStartNotification() {
                notificationManager?.notify(3, builderAsr?.build())
            }
            //Maghrib
            private fun maghribSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderMaghrib = NotificationCompat.Builder(context, "namazTimings")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Maghrib")
                    .setContentText("Content Text - Maghrib")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            private fun maghribStartNotification() {
                notificationManager?.notify(4, builderMaghrib?.build())
            }
            //Isha
            private fun ishaSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderIsha = NotificationCompat.Builder(context, "namazTimings")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Isha")
                    .setContentText("Content Text - Isha")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            private fun ishaStartNotification() {
                notificationManager?.notify(5, builderIsha?.build())
            }

    private fun getRandomRequestCode() = Utils.getRandomInt()
}