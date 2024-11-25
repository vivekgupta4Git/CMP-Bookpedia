package com.plcoding.bookpedia.book.data.database

import androidx.room.TypeConverter

object StringListTypeConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",").map { it.trim() }
    }

    @TypeConverter
    fun toString(value: List<String>): String {
        return value.joinToString(",")
    }
}