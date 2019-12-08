package com.chaeniiz.weatherdiary.data

import android.content.Context
import com.chaeniiz.weatherdiary.data.network.api.CurrentWeatherApi
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(
    context: Context
) : ApiClientSpec {

    companion object {
        const val CONNECT_TIMEOUT = "connect_timeout"
        const val READ_TIMEOUT = "read_timeout"
        const val WRITE_TIMEOUT = "write_timeout"
    }

    private val gson by lazy {
        GsonBuilder()
            .setLenient()
            .create()
    }

    private val adapter: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(buildOkHttpClient().build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun buildOkHttpClient(
        timeout: Long = 20
    ): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(createTimeOutInterceptor())
    }

    private fun createTimeOutInterceptor() = Interceptor { chain ->
        val request = chain.request()
        val requestBuilder = request.newBuilder()

        var connectTimeout = chain.connectTimeoutMillis()
        var readTimeout = chain.readTimeoutMillis()
        var writeTimeout = chain.writeTimeoutMillis()

        request.header(CONNECT_TIMEOUT)?.let { connectTimeout = it.toInt() }
        request.header(READ_TIMEOUT)?.let { readTimeout = it.toInt() }
        request.header(WRITE_TIMEOUT)?.let { writeTimeout = it.toInt() }

        chain
            .withConnectTimeout(connectTimeout, TimeUnit.SECONDS)
            .withReadTimeout(readTimeout, TimeUnit.SECONDS)
            .withWriteTimeout(writeTimeout, TimeUnit.SECONDS)
            .proceed(requestBuilder.build())
    }

    override val currentWeatherApi: CurrentWeatherApi by lazy {
        adapter.create(CurrentWeatherApi::class.java)
    }
}
