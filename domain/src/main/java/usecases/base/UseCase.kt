package usecases.base

import io.reactivex.disposables.CompositeDisposable

abstract class UseCase {

    protected val disposable: CompositeDisposable = CompositeDisposable()

    protected open fun clear() {
        disposable.clear()
    }

    protected open fun dispose() {
        disposable.dispose()
    }

    @Suppress("NOTHING_TO_INLINE")
    protected inline fun getCallerStackTrace(): Array<StackTraceElement> {
        val currentMethodStackTrace = Throwable().stackTrace
        val stackTraceToDrop = 1 // 1: 이 함수를 부른 함수 f, 2: f를 호출한 함수
        return currentMethodStackTrace.drop(stackTraceToDrop).toTypedArray()
    }

    protected fun Array<StackTraceElement>.appendTo(e: Throwable) {
        e.stackTrace = this + e.stackTrace
    }
}
