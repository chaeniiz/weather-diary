package com.chaeniiz.weatherdiary.data.network.response.model

import com.chaeniiz.weatherdiary.data.network.response.Response
import com.google.gson.annotations.SerializedName
import com.chaeniiz.entity.entities.Weather as Entity

data class Weather(
    @SerializedName("id")
    val id: Int,

    @SerializedName("main")
    val main: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("icon")
    val icon: String
) : Response

fun Weather.toEntity(): Entity = Entity(
    id,
    main,
    description,
    icon
)
