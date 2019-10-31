package com.chaeniiz.entity.entities

import java.util.*

data class Diary(
    val id: Int,
    val location: String,
    val weather: Weather,
    val content: String,
    val updatedAt: Date
)
