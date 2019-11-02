package com.chaeniiz.weatherdiary.data.local

import androidx.room.*
import com.chaeniiz.weatherdiary.data.local.model.Diary

@Dao
interface DiaryDao {
    @Query("SELECT * FROM diary")
    fun getDiaries(): List<Diary>

    @Query("SELECT * FROM diary where id = :id")
    fun getDiary(id: Int): Diary

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDiary(diary: Diary)

    @Update
    fun updateDiary(diary: Diary)

    @Delete
    fun deleteDiary(diary: Diary)
}
