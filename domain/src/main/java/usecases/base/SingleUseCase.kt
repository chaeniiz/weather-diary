package usecases.base

import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver

abstract class SingleUseCase<T>(
    private val executorScheduler: Scheduler,
    private val postExecutionScheduler: Scheduler
) : UseCase() {

    protected abstract fun buildUseCaseSingle(): Single<T>

    fun execute(useCaseObserver: DisposableSingleObserver<T>) {
        val callerStackTrace = getCallerStackTrace()
        if (disposable.isDisposed.not()) {
            get().doOnError { callerStackTrace.appendTo(it) }
                .subscribeWith(useCaseObserver)
                .let(disposable::add)
        }
    }

    fun get(): Single<T> = buildUseCaseSingle()
        .subscribeOn(executorScheduler)
        .observeOn(postExecutionScheduler)
}
