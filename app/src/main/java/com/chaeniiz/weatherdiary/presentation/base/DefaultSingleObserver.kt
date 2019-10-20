package com.chaeniiz.weatherdiary.presentation.base

import androidx.annotation.CallSuper
import io.reactivex.observers.DisposableSingleObserver

open class DefaultSingleObserver<T : Any> : DisposableSingleObserver<T>(),
    DefaultErrorHandler {

    override fun onSuccess(t: T) {
        /* explicitly empty */
    }

    @CallSuper
    override fun onError(e: Throwable) {
        super.onError(e)
    }
}
