package usecases

import com.chaeniiz.domain.repositories.DiaryRepository
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import usecases.base.CompletableDisposableUseCase

class DeleteDiary(
    private val repository: DiaryRepository,
    executorScheduler: Scheduler = Schedulers.io(),
    postExecutionScheduler: Scheduler = AndroidSchedulers.mainThread()
) : CompletableDisposableUseCase(executorScheduler, postExecutionScheduler) {

    var id: Int? = null

    override fun buildUseCaseCompletable(): Completable =
        id?.run {
            repository.deleteDiary(this)
        } ?: throw IllegalArgumentException()
}
