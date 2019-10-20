package com.chaeniiz.weatherdiary.data.network.response

import com.chaeniiz.weatherdiary.data.network.response.model.*
import com.google.gson.annotations.SerializedName
import com.chaeniiz.entity.entities.CurrentWeather as Entity

data class GetCurrentWeatherResponse(
    @SerializedName("coord")
    val coord: Coord,

    @SerializedName("weather")
    val weathers: List<Weather>,

    @SerializedName("base")
    val base: String,

    @SerializedName("main")
    val main: Main,

    @SerializedName("visibility")
    val visibility: Double,

    @SerializedName("wind")
    val wind: Wind,

    @SerializedName("clouds")
    val clouds: Clouds,

    @SerializedName("dt")
    val dt: Double,

    @SerializedName("sys")
    val sys: Sys,

    @SerializedName("timezone")
    val timeZone: Double,

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("cod")
    val cod: Int
) : Response

fun GetCurrentWeatherResponse.toEntity(): Entity =
    Entity(
        coord.toEntity(),
        weathers.map { it.toEntity() },
        base,
        main.toEntity(),
        visibility,
        wind.toEntity(),
        clouds.toEntity(),
        dt,
        sys.toEntity(),
        timeZone,
        id,
        name,
        cod
    )
