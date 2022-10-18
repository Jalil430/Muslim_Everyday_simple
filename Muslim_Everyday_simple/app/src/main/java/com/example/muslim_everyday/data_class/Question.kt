package com.example.muslim_everyday.data_class

data class Question(val q: String, val a1: String, val a2: String)

object QuestionsList {
    fun getQuestionsList() = listOf (
        Question("Встаёте ли вы на утренний намаз?", "Включить будильник на утренний намаз?", "Все равно включить будильник на утренний намаз?"),
        Question("Читаете ли вы Тасбих после каждого обязательного намаза?", "Начать изучение Тасбиха и уведомлять об этом?", "Все равно начать изучение Тасбиха и уведомлять об этом?")
    )
}
