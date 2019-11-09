package com.chaeniiz.weatherdiary.presentation.base

import androidx.annotation.CallSuper
import io.reactivex.observers.DisposableCompletableObserver

open class DefaultCompletableObserver : DisposableCompletableObserver(), DefaultErrorHandler {

    override fun onComplete() {
        /* explicitly empty */
    }

    @CallSuper
    override fun onError(e: Throwable) {
        super.onError(e)
    }
}
