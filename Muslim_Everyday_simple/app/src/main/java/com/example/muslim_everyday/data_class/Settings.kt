package com.example.muslim_everyday.data_class

data class Settings(val t: String, val s: String)

object SettingsList {
    fun getSettingsList() = listOf (
        Settings("АЗАН","Уведомлять об утреннем намазе")
    )
}
