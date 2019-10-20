package com.chaeniiz.weatherdiary.data.network.response.model

import com.chaeniiz.weatherdiary.data.network.response.Response
import com.google.gson.annotations.SerializedName
import com.chaeniiz.entity.entities.Wind as Entity

data class Wind(
    @SerializedName("speed")
    val speed: Double,

    @SerializedName("deg")
    val deg: Double
) : Response

fun Wind.toEntity(): Entity = Entity(
    speed,
    deg
)
