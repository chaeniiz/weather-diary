package com.chaeniiz.weatherdiary

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherDiary : MultiDexApplication() {

    val api: ApiClientSpec by inject()

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@WeatherDiary)
            modules(appModule)
        }
    }
}
