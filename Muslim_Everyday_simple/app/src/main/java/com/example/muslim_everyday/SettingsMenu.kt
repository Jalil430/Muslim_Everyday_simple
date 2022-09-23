package com.example.muslim_everyday

import android.app.*
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.muslim_everyday.data_class.Settings
import com.example.muslim_everyday.databinding.SettingsMenuBinding
import com.example.muslim_everyday.receiver.NotificationReceiver
import com.example.muslim_everyday.recycler_view.SettingsMenuRVAdapter
import org.json.JSONException
import java.util.*

class SettingsMenu : AppCompatActivity() {
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
        private lateinit var alarmManager: AlarmManager
        private var isNotificationEnabled = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)

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
        intent = Intent(this, NotificationReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun cancelNotification() {
        alarmManager.cancel(pendingIntent)
    }

    private fun setCalendar() {
        calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR, 11)
        calendar.set(Calendar.MINUTE, 4)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
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
}