package com.chaeniiz.weatherdiary.presentation.inject

import com.chaeniiz.weatherdiary.data.ApiClient
import com.chaeniiz.weatherdiary.data.ApiClientSpec
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule: Module = module {
    single { ApiClient(androidApplication()) as ApiClientSpec }
}
