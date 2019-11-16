package com.chaeniiz.weatherdiary.data.local

import android.content.Context
import com.chaeniiz.domain.repositories.DiaryRepository
import com.chaeniiz.entity.entities.Diary
import com.chaeniiz.weatherdiary.data.local.database.DiaryDatabase
import com.chaeniiz.weatherdiary.data.local.model.toEntity
import com.chaeniiz.weatherdiary.data.local.model.toLocalModel
import io.reactivex.Completable
import io.reactivex.Single

class DiaryRepository(context: Context) : DiaryRepository {
    private val dao = DiaryDatabase.getInstance(context).diaryDao()

    override fun getDiaries(): Single<List<Diary>> =
        Single.fromCallable {
            dao.getDiaries().map {
                it.toEntity()
            }
        }

    override fun getDiary(id: Int): Single<Diary> =
        Single.fromCallable {
            dao.getDiary(id)?.toEntity()
        }

    override fun insertDiary(diary: Diary): Completable =
        Completable.fromCallable {
            dao.insertDiary(diary.toLocalModel())
        }

    override fun updateDiary(diary: Diary): Completable =
        Completable.fromCallable {
            dao.updateDiary(
                diary.id,
                diary.location,
                diary.weather,
                diary.content,
                diary.updatedAt
            )
        }

    override fun deleteDiary(diary: Diary): Completable =
        Completable.fromCallable {
            dao.deleteDiary(diary.toLocalModel())
        }
}
