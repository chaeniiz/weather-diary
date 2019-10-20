package com.chaeniiz.entity.entities

data class CurrentWeather(
    val coord: Coord,
    val weather: List<Weather>,
    val base: String,
    val main: Main,
    val visibility: Double,
    val wind: Wind,
    val clouds: Clouds,
    val dt: Double,
    val sys: Sys,
    val timeZone: Double,
    val id: Int,
    val name: String,
    val cod: Int
)
