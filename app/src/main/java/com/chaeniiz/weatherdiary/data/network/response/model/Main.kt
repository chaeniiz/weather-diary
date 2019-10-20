package com.chaeniiz.weatherdiary.data.network.response.model

import com.chaeniiz.weatherdiary.data.network.response.Response
import com.google.gson.annotations.SerializedName
import com.chaeniiz.entity.entities.Main as Entity

data class Main(
    @SerializedName("temp")
    val temp: Double,

    @SerializedName("pressure")
    val pressure: Double,

    @SerializedName("humidity")
    val humidity: Double,

    @SerializedName("temp_min")
    val minTemp: Double,

    @SerializedName("temp_max")
    val maxTemp: Double
) : Response

fun Main.toEntity(): Entity = Entity(
    temp,
    pressure,
    humidity,
    minTemp,
    maxTemp
)
