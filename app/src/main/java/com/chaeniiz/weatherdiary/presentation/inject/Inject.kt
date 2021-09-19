package com.chaeniiz.weatherdiary.presentation.inject

import com.chaeniiz.weatherdiary.data.ApiClient
import com.chaeniiz.weatherdiary.data.ApiClientSpec
import com.chaeniiz.weatherdiary.presentation.citiesdialog.CitiesDialogViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    single { ApiClient(androidApplication()) as ApiClientSpec }
}
val viewModelModule = module {
    viewModel { CitiesDialogViewModel(get()) }
}
