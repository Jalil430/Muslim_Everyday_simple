package com.example.muslim_everyday.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.muslim_everyday.R
import com.example.muslim_everyday.SettingsMenu
import com.example.muslim_everyday.util.RandomIntUtil

class NotificationReceiver() : BroadcastReceiver() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var builderFajr: NotificationCompat.Builder
    private lateinit var builderDhuhr: NotificationCompat.Builder
    private lateinit var builderAsr: NotificationCompat.Builder
    private lateinit var builderMaghrib: NotificationCompat.Builder
    private lateinit var builderIsha: NotificationCompat.Builder

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Win!!!", Toast.LENGTH_LONG).show()

        createNotificationChannel(context)
        fajrSetNotification(context)
        fajrStartNotification()
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "FajrReminderChannel"
            val description = "Channel Fajr"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("namazTimings", name, importance)
            channel.description = description

            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        } else {
            Toast.makeText(context, "Alarm failed", Toast.LENGTH_LONG).show()
        }
    }

    // Namaz timings methods
        // Fajr
            fun fajrSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderFajr = NotificationCompat.Builder(context, "Fajr")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Fajr")
                    .setContentText("Content Text - Fajr")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            fun fajrStartNotification() {
                notificationManager.notify(1, builderFajr.build())
            }
            // Dhuhr
            fun dhuhrSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderDhuhr = NotificationCompat.Builder(context, "Dhuhr")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Dhuhr")
                    .setContentText("Content Text - Dhuhr")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            fun dhuhrStartNotification() {
                notificationManager.notify(1, builderDhuhr.build())
            }
            // Asr
            fun asrSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderAsr = NotificationCompat.Builder(context, "Asr")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Asr")
                    .setContentText("Content Text - Asr")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            fun asrStartNotification() {
                notificationManager.notify(1, builderAsr.build())
            }
            //Maghrib
            fun maghribSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderMaghrib = NotificationCompat.Builder(context, "Maghrib")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Maghrib")
                    .setContentText("Content Text - Maghrib")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            fun maghribStartNotification() {
                notificationManager.notify(1, builderMaghrib.build())
            }
            //Isha
            fun ishaSetNotification(context: Context) {
                val intent = Intent(context, SettingsMenu::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                val pendingIntent = PendingIntent.getActivity(context, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

                builderIsha = NotificationCompat.Builder(context, "Isha")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Title - Isha")
                    .setContentText("Content Text - Isha")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
            }
            fun ishaStartNotification() {
                notificationManager.notify(1, builderIsha.build())
            }

    private fun getRandomRequestCode() = RandomIntUtil.getRandomInt()
}