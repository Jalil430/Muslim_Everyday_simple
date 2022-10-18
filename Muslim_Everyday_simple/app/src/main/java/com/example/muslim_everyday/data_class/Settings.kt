package com.example.muslim_everyday.data_class

data class Settings(val s: String)

object SettingsList {
    fun getSettingsList() = listOf (
        Settings("Уведомлять об утреннем намазе")
    )
}
