package usecases

import com.chaeniiz.domain.repositories.DiaryRepository
import com.chaeniiz.entity.entities.Diary
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import usecases.base.SingleDisposableUseCase

class GetDiaries(
    private val repository: DiaryRepository,
    executorScheduler: Scheduler = Schedulers.io(),
    postExecutionScheduler: Scheduler = AndroidSchedulers.mainThread()
) : SingleDisposableUseCase<List<Diary>>(executorScheduler, postExecutionScheduler) {

    override fun buildUseCaseSingle(): Single<List<Diary>> =
        repository.getDiaries()
}
