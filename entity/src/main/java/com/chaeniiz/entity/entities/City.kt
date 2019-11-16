package com.chaeniiz.entity.entities

enum class City(val value: String, val formattedString: String) {
    SEOUL("Seoul", "서울"),
    INCHEON("Incheon", "인천"),
    DAEJEON("Daejeon", "대전"),
    GWANGJU("Gwangju", "광주"),
    BUSAN("Busan", "부산"),
    DAEGU("Daegu", "대구"),
    ULSAN("Ulsan", "울산"),
    JEJU("Jeju", "제주");

    companion object {
        fun get(value: String) : City =
            values().find { it.value == value } ?: throw IllegalArgumentException("$value not defined")
    }
}
