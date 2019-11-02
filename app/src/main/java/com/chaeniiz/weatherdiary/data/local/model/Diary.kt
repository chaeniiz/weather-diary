package com.chaeniiz.weatherdiary.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Diary")
class Diary(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "weather") val weather: Weather,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Date
)
