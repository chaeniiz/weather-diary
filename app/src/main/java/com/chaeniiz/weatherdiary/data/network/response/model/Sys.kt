package com.chaeniiz.weatherdiary.data.network.response.model

import com.chaeniiz.weatherdiary.data.network.response.Response
import com.google.gson.annotations.SerializedName
import com.chaeniiz.entity.entities.Sys as Entity

data class Sys(
    @SerializedName("type")
    val type: Int,

    @SerializedName("id")
    val id: Int,

    @SerializedName("message")
    val message: Double,

    @SerializedName("country")
    val country: String,

    @SerializedName("sunrise")
    val sunrise: Double,

    @SerializedName("sunset")
    val sunset: Double
) : Response

fun Sys.toEntity(): Entity = Entity(
    type,
    id,
    message,
    country,
    sunrise,
    sunset
)
