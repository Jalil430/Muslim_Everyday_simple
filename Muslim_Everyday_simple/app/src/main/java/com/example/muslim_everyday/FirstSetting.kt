package com.example.muslim_everyday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.muslim_everyday.recycler_view.FirstSettingRVAdapter
import com.example.muslim_everyday.data_class.Question

class FirstSetting : AppCompatActivity() {
    private val questionsList = listOf<Question>(
        Question("Встаёте ли вы на утренний намаз?"),
        Question("Читаете ли вы Тасбих после каждого обязательного намаза?")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.first_setting)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FirstSettingRVAdapter(questionsList)
    }
}