package com.chaeniiz.entity.entities

import java.util.*

data class Diary(
    val id: Int = 0,
    val location: String,
    val weather: String,
    val content: String,
    val updatedAt: Date
)
