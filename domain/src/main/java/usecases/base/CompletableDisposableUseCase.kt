package usecases.base

import io.reactivex.Scheduler

abstract class CompletableDisposableUseCase(
    executorScheduler: Scheduler,
    postExecutionScheduler: Scheduler
) : CompletableUseCase(executorScheduler,postExecutionScheduler) {

    public override fun clear() {
        super.clear()
    }

    public override fun dispose() {
        super.dispose()
    }
}
