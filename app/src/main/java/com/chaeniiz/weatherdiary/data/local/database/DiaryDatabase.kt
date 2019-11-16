package com.chaeniiz.weatherdiary.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.chaeniiz.weatherdiary.data.local.Converters
import com.chaeniiz.weatherdiary.data.local.DiaryDao
import com.chaeniiz.weatherdiary.data.local.model.Diary

@Database(entities = [Diary::class], version = 2)
@TypeConverters(Converters::class)
abstract class DiaryDatabase: RoomDatabase() {
    abstract fun diaryDao(): DiaryDao

    companion object {
        private lateinit var diaryDatabase: DiaryDatabase

        fun getInstance(context: Context): DiaryDatabase {
            if (::diaryDatabase.isInitialized.not()) {
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
}
