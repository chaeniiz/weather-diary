package com.chaeniiz.weatherdiary.data.local

import androidx.room.*
import com.chaeniiz.weatherdiary.data.local.model.Diary
import java.util.*

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun getDiaries(): List<Diary>

    @Query("SELECT * FROM diary where id = :id")
    fun getDiary(id: Int): Diary?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiary(diary: Diary)

    @Query("UPDATE diary SET location = :location, weather = :weather, content = :content, updated_at = :updatedAt WHERE id = :id")
    fun updateDiary(id: Int, location: String, weather: String, content: String, updatedAt: Date)

    @Delete
    fun deleteDiary(diary: Diary)
}
