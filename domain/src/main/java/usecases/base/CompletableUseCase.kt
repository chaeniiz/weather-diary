package usecases.base

import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableCompletableObserver

abstract class CompletableUseCase(
    private val executorScheduler: Scheduler,
    private val postExecutionScheduler: Scheduler
) : UseCase() {

    protected abstract fun buildUseCaseCompletable(): Completable

    fun execute(useCaseObserver: DisposableCompletableObserver) {
        val callerStackTrace = getCallerStackTrace()
        if (disposable.isDisposed.not()) {
            get().doOnError { callerStackTrace.appendTo(it) }
                .subscribeWith(useCaseObserver)
                .let(disposable::add)
        }
    }

    fun get(): Completable = buildUseCaseCompletable()
        .subscribeOn(executorScheduler)
        .observeOn(postExecutionScheduler)
}
