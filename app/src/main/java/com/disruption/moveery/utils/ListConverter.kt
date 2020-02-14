package com.disruption.moveery.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    @TypeConverter
    fun stringToIntList(data: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun intListToString(genreIds: List<Int>): String {
        return Gson().toJson(genreIds)
    }
}