package com.chaeniiz.weatherdiary.data.network.response.model

import com.chaeniiz.weatherdiary.data.network.response.Response
import com.google.gson.annotations.SerializedName
import com.chaeniiz.entity.entities.Clouds as Entity

data class Clouds(
    @SerializedName("all")
    val all: Int
) : Response

fun Clouds.toEntity(): Entity = Entity(all)
