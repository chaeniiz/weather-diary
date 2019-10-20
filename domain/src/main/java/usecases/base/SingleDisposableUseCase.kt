package usecases.base

import io.reactivex.Scheduler

abstract class SingleDisposableUseCase<T>(
    executorScheduler: Scheduler,
    postExecutionScheduler: Scheduler
) : SingleUseCase<T>(executorScheduler, postExecutionScheduler) {

    public override fun clear() {
        super.clear()
    }

    public override fun dispose() {
        super.dispose()
    }
}
