package com.example.muslim_everyday

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.muslim_everyday.data_class.Settings
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.recycler_view.MyViewHolder
import com.example.muslim_everyday.recycler_view.SettingsMenuRVAdapter
import com.example.muslim_everyday.service.AlarmService
import com.example.muslim_everyday.util.RandomIntUtil
import com.example.muslim_everyday.view_model.ViewModel_rv
import org.json.JSONException
import java.util.*
import java.util.Date.from

open class SettingsMenu : AppCompatActivity() {
    private lateinit var binding: SettingsMenuBinding
    // Initialize list of settings that contain in this activity
    private val settingsList = listOf (
        Settings("Уведомлять об утреннем намазе")
    )
    // API call
        private var mRequestQueue: RequestQueue? = null
        // url
        private val url = "https://muslimsalat.com/Oyskhara.json?key=084c27252d935e5c202be396026a5adf"
        // Tag used to cancel the request
        private val tag_json_obj = "json_obj_req"
    // Prayer timings variables
        private lateinit var country : String
        private lateinit var state : String
        private lateinit var city : String
        private lateinit var location : String
        private lateinit var date : String

        private lateinit var mFajr : String
        private lateinit var mDhuhr : String
        private lateinit var mAsr : String
        private lateinit var mMaghrib : String
        private lateinit var mIsha : String
    // Notification
        private lateinit var pendingIntent: PendingIntent
        private lateinit var calendar: Calendar
        private lateinit var notificationManager: NotificationManager
        private lateinit var builder: NotificationCompat.Builder
        private lateinit var alarmService: AlarmService
        private var isNotificationEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)

        alarmService = AlarmService(this)

        // Get prayer timings
        getData()
        ViewModelProvider(this)

        // Initialize view binding
        binding = SettingsMenuBinding.inflate(layoutInflater)

        // Initialize recycler view
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SettingsMenuRVAdapter(settingsList, this) {
            isNotificationEnabled = it
            if (isNotificationEnabled) {
                enableNotification()
            } else {
                cancelNotification()
            }
        }
    }

    private fun enableNotification() {
        setCalendar()
        alarmService.setExactAlarm(calendar.timeInMillis)
    }

    private fun cancelNotification() {
        alarmService.cancelNotification()
    }

    private fun setCalendar() {
        calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 13)
        calendar.set(Calendar.MINUTE, 14)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
    }

    fun setNotification() {
        val intent = Intent(this, SettingsMenu::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        pendingIntent = PendingIntent.getActivity(this, getRandomRequestCode(), intent, PendingIntent.FLAG_IMMUTABLE)

        builder = NotificationCompat.Builder(this, "Fajr")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Title")
            .setContentText("Content Text")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name : CharSequence = "FajrReminderChannel"
            val description = "Channel for alarm manager"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel("Fajr", name, importance)
            channel.description = description

            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        } else {
            Toast.makeText(this, "Alarm failed", Toast.LENGTH_LONG).show()
        }
    }

    fun startFajrNotification() {
        notificationManager.notify(1, builder.build())
    }

    private fun getData() {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(this)

        // String Request initialized
        val request = JsonObjectRequest(Request.Method.GET, url, null, {
                response ->
            try {
                // Get location
                country = response.get("country").toString()
                state = response.get("state").toString()
                city = response.get("city").toString()
                location = "$country, $state, $city"

                // get date
                date = response.getJSONArray("items").getJSONObject(0).get("date_for").toString()

                // Get namaz timings
                mFajr = response.getJSONArray("items").getJSONObject(0).get("fajr").toString()
                mDhuhr = response.getJSONArray("items").getJSONObject(0).get("dhuhr").toString()
                mAsr = response.getJSONArray("items").getJSONObject(0).get("asr").toString()
                mMaghrib = response.getJSONArray("items").getJSONObject(0).get("maghrib").toString()
                mIsha = response.getJSONArray("items").getJSONObject(0).get("isha").toString()
            } catch(e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        mRequestQueue?.add(request)
    }

    private fun getRandomRequestCode() = RandomIntUtil.getRandomInt()
}