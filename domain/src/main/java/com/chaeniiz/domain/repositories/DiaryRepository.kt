package com.chaeniiz.domain.repositories

import com.chaeniiz.entity.entities.Diary
import io.reactivex.Completable
import io.reactivex.Single

interface DiaryRepository {
    fun getDiaries(): Single<List<Diary>>
    fun getDiary(id: Int): Single<Diary>
    fun insertDiary(diary: Diary): Completable
    fun updateDiary(diary: Diary): Completable
    fun deleteDiary(diary: Diary): Completable
}
