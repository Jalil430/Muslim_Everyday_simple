package com.example.muslim_everyday

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.Settings
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.receiver.RepeatingAlarmEveryday
import com.example.muslim_everyday.recycler_view.SettingsMenuRVAdapter
import com.example.muslim_everyday.util.NotificationUtils
import java.lang.Exception
import java.util.*

class SettingsMenu : AppCompatActivity() {
    private var binding: SettingsMenuBinding? = null
    private var repeatingAlarmManager: AlarmManager? = null
    private var repeatingPendingIntent: PendingIntent? = null
    private var isNotificationEnabled = false
    // Initialize list of settings that contain in this activity
        private val settingsList = listOf (
            Settings("Уведомлять об утреннем намазе")
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)

        // Initialize view binding
        binding = SettingsMenuBinding.inflate(layoutInflater)

        // Initialize recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingsMenuRVAdapter(settingsList, this) {
            isNotificationEnabled = it
            if (isNotificationEnabled) {
                try {
                    NotificationUtils.enableNotification(this)
                    setRepeatingAlarm()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("MyApp", "ji")
                }
            } else {
                try {
                    NotificationUtils.cancelNotification()
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("MyApp", "ji")
                }
            }
        }
    } // SHARED PREFERENCES

    private fun setRepeatingAlarm() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val intent = Intent(this, RepeatingAlarmEveryday::class.java)

        repeatingPendingIntent = PendingIntent.getBroadcast(
            this,
            NotificationUtils.getRandomInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        repeatingAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (System.currentTimeMillis() < calendar.timeInMillis) {
            repeatingAlarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                repeatingPendingIntent
            )
        }
    }
}