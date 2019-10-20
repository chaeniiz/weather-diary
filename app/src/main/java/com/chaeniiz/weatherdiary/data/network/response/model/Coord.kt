package com.chaeniiz.weatherdiary.data.network.response.model

import com.chaeniiz.weatherdiary.data.network.response.Response
import com.google.gson.annotations.SerializedName
import com.chaeniiz.entity.entities.Coord as Entity

data class Coord(
    @SerializedName("lon")
    val lon: Double,

    @SerializedName("lat")
    val lat: Double
) : Response

fun Coord.toEntity(): Entity = Entity(
    lon,
    lat
)
