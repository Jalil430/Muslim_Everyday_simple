package com.example.muslim_everyday.service

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.muslim_everyday.receiver.AlarmReceiver
import com.example.muslim_everyday.util.RandomIntUtil

class AlarmService(private val context: Context) {
    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?

    fun setExactAlarm(timeInMillis: Long) {
        // Does reach this ))
        Toast.makeText(context, "set exact alarm", Toast.LENGTH_LONG).show()

        try {
            setAlarm(
                timeInMillis,
                getPendingIntent(getIntent())
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "set exact alarm2", Toast.LENGTH_LONG).show()
        }
    }

    private fun getPendingIntent(intent: Intent) =
        PendingIntent.getBroadcast(
            context,
            getRandomRequestCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

    private fun setAlarm(timeInMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() * 1000,
            pendingIntent
        )
    }

    fun cancelNotification() {
        alarmManager?.cancel(getPendingIntent(getIntent()))
    }

    private fun getIntent() = Intent(context, AlarmReceiver::class.java)

    private fun getRandomRequestCode() = RandomIntUtil.getRandomInt()
}