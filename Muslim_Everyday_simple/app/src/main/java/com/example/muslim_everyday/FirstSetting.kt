package com.example.muslim_everyday

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.muslim_everyday.data_class.Question
import com.example.muslim_everyday.recycler_view.FirstSettingRVAdapter
import com.example.muslim_everyday.view_model.ViewModel_rv
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class FirstSetting : AppCompatActivity() {
    // Initialize list of questions this activity gives
    private val questionsList = listOf (
        Question("Встаёте ли вы на утренний намаз?", "Включить будильник на утренний намаз?"),
        Question("Читаете ли вы Тасбих после каждого обязательного намаза?", "Начать изучение Тасбиха и уведомлять об этом?")
    )
    // Initialize view model
    private val viewModel: ViewModel_rv by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_setting)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FirstSettingRVAdapter(questionsList)

        val btnNext = findViewById<Button>(R.id.btnNext)
        btnNext.setOnClickListener {
            val intent = Intent(this, SettingsMenu::class.java)
            startActivity(intent)
        }
    }
}

