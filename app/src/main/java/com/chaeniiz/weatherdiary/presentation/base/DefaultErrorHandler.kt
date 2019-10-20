package com.chaeniiz.weatherdiary.presentation.base

import androidx.annotation.CallSuper
import com.chaeniiz.weatherdiary.BuildConfig
import retrofit2.HttpException
import java.net.ConnectException
import java.net.UnknownHostException

interface DefaultErrorHandler {

    companion object {
        fun handle(e: Throwable) {
            if (BuildConfig.DEBUG) {
                if ((e is HttpException).not()) {
                    e.printStackTrace()
                }
            }

            when (e) {
                is UnknownHostException,
                is ConnectException -> return
            }
        }
    }

    @CallSuper
    fun onError(e: Throwable) =
        handle(e)
}
