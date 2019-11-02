package com.chaeniiz.weatherdiary.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.chaeniiz.weatherdiary.data.local.DiaryDao
import com.chaeniiz.weatherdiary.data.local.model.Diary

@Database(entities = [Diary::class], version = 1)
abstract class DiaryDatabase: RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        private var diaryDatabase: DiaryDatabase? = null

        fun getInstance(context: Context): DiaryDatabase? {
            if (diaryDatabase == null) {
                synchronized(DiaryDatabase::class) {
                    diaryDatabase = Room.databaseBuilder(
                        context.applicationContext,
                        DiaryDatabase::class.java,
                        "diary.db"
                        ).fallbackToDestructiveMigration()
                        .build()
                }
            }
            return diaryDatabase
        }
    }

    fun destroy() {
        diaryDatabase = null
    }
}