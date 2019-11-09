package usecases

import com.chaeniiz.domain.repositories.DiaryRepository
import com.chaeniiz.entity.entities.Diary
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import usecases.base.SingleDisposableUseCase

class GetDiary(
    private val repository: DiaryRepository,
    executorScheduler: Scheduler = Schedulers.io(),
    postExecutionScheduler: Scheduler = AndroidSchedulers.mainThread()
) : SingleDisposableUseCase<Diary>(executorScheduler, postExecutionScheduler) {

    var id: Int? = null

    override fun buildUseCaseSingle(): Single<Diary> =
        id?.run {
            repository.getDiary(this)
        } ?: Single.error(Exception())
}
