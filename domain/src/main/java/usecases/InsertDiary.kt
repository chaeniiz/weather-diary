package usecases

import com.chaeniiz.domain.repositories.DiaryRepository
import com.chaeniiz.entity.entities.Diary
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import usecases.base.CompletableDisposableUseCase

class InsertDiary(
    private val repository: DiaryRepository,
    executorScheduler: Scheduler = Schedulers.io(),
    postExecutionScheduler: Scheduler = AndroidSchedulers.mainThread()
) : CompletableDisposableUseCase(executorScheduler, postExecutionScheduler) {

    lateinit var diary: Diary

    override fun buildUseCaseCompletable(): Completable =
        repository.insertDiary(diary)
}
