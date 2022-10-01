package com.example.muslim_everyday

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import com.example.muslim_everyday.util.Constants
import com.example.muslim_everyday.util.RandomIntUtil
import org.json.JSONException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.Delegates

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

        private lateinit var calendarDateFormat : SimpleDateFormat
        private lateinit var calendarFajrDate : Date
        private lateinit var calendarDhuhrDate : Date
        private lateinit var calendarAsrDate : Date
        private lateinit var calendarMaghribDate : Date
        private lateinit var calendarIshaDate : Date

    // Notification
        private lateinit var pendingIntent: PendingIntent
        private lateinit var calendar: Calendar
        private lateinit var alarmManager: AlarmManager
        private var isNotificationEnabled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_menu)

        // Get prayer timings
        ViewModelProvider(this)
        getData()

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
        getData()
        setCalendar()
        intent = Intent(this, NotificationReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this,
            getRandomRequestCode(),
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 10000,
            pendingIntent
        )
    }

    private fun cancelNotification() {
        alarmManager.cancel(pendingIntent)
    }

    private fun setCalendar() {
        convertStrings()
        calendar = Calendar.getInstance()

        val fajrTime = Calendar.getInstance()
        fajrTime.time = calendarFajrDate
        calendar.timeInMillis = fajrTime.timeInMillis

        val dhuhrTime = Calendar.getInstance()
        dhuhrTime.time = calendarDhuhrDate

        val asrTime = Calendar.getInstance()
        asrTime.time = calendarAsrDate

        val maghribTime = Calendar.getInstance()
        maghribTime.time = calendarMaghribDate

        val ishaTime = Calendar.getInstance()
        ishaTime.time = calendarIshaDate

        if (System.currentTimeMillis() > fajrTime.timeInMillis) {
            calendar.timeInMillis = dhuhrTime.timeInMillis
            Toast.makeText(this, "${asrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() > dhuhrTime.timeInMillis) {
            calendar.timeInMillis = asrTime.timeInMillis
            Toast.makeText(this, "${maghribTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() > asrTime.timeInMillis) {
            calendar.timeInMillis = maghribTime.timeInMillis
            Toast.makeText(this, "${ishaTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() > maghribTime.timeInMillis) {
            calendar.timeInMillis = ishaTime.timeInMillis
            Toast.makeText(this, "${fajrTime.time}", Toast.LENGTH_LONG).show()
        }
        if (System.currentTimeMillis() > ishaTime.timeInMillis){
            calendar.timeInMillis = fajrTime.timeInMillis
            Toast.makeText(this, "${dhuhrTime.time}", Toast.LENGTH_LONG).show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun convertStrings() {
        val date12Format = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
        val date24Format = SimpleDateFormat("HH:mm:ss")

        val cFajr = date12Format.parse(mFajr)?.let { date24Format.format(it) }
        val cDhuhr = date12Format.parse(mDhuhr)?.let { date24Format.format(it) }
        val cAsr = date12Format.parse(mAsr)?.let { date24Format.format(it) }
        val cMaghrib = date12Format.parse(mMaghrib)?.let { date24Format.format(it) }
        val cIsha = date12Format.parse(mIsha)?.let { date24Format.format(it) }

        val calendarFajrTime = "$date $cFajr"
        val calendarDhuhrTime = "$date $cDhuhr"
        val calendarAsrTime = "$date $cAsr"
        val calendarMaghribTime = "$date $cMaghrib"
        val calendarIshaTime = "$date $cIsha"

        calendarDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

        calendarFajrDate = parseDate(calendarFajrTime)
        calendarDhuhrDate = parseDate(calendarDhuhrTime)
        calendarAsrDate = parseDate(calendarAsrTime)
        calendarMaghribDate = parseDate(calendarMaghribTime)
        calendarIshaDate = parseDate(calendarIshaTime)
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
                Toast.makeText(this, date, Toast.LENGTH_LONG).show()
            } catch(e: JSONException) {
                e.printStackTrace()
            }
        }, { error -> error.printStackTrace() })
        mRequestQueue?.add(request)
    }

    private fun parseDate(source: String) = calendarDateFormat.parse(source) as Date

    private fun getRandomRequestCode() = RandomIntUtil.getRandomInt()
}