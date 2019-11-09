package com.chaeniiz.weatherdiary.data.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*
import com.chaeniiz.entity.entities.Diary as DiaryEntity

@Entity(tableName = "Diary")
class Diary(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "location") val location: String,
    @ColumnInfo(name = "weather") val weather: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "updated_at") val updatedAt: Date
)

fun Diary.toEntity(): DiaryEntity = DiaryEntity(
    id,
    location,
    weather,
    content,
    updatedAt
)
fun DiaryEntity.toLocalModel() = Diary(
    id,
    location,
    weather,
    content,
    updatedAt
)
