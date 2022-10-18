package com.example.muslim_everyday.util

import java.util.concurrent.atomic.AtomicInteger

object Utils {
    private val seed = AtomicInteger()
    fun getRandomInt() = seed.getAndIncrement() + System.currentTimeMillis().toInt()
}