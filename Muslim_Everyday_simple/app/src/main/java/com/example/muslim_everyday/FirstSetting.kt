package com.example.muslim_everyday

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.data_class.Question
import com.example.muslim_everyday.recycler_view.FirstSettingRVAdapter

class FirstSetting : AppCompatActivity() {
    private val questionsList = listOf<Question>(
        Question("Встаёте ли вы на утренний намаз?", "Включить будильник на утренний намаз?"),
        Question("Читаете ли вы Тасбих после каждого обязательного намаза?", "Начать изучение Тасбиха и уведомлять об этом?")
    )
    private val viewModel: ViewModel_rv by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_setting)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FirstSettingRVAdapter(questionsList)
    }
}