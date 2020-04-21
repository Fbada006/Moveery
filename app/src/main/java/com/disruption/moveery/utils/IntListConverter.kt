package com.disruption.moveery.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**A [TypeConverter] to help with storing a [List] of Ints with Room*/
class IntListConverter {

    /**Convert string to list*/
    @TypeConverter
    fun stringToIntList(data: String): List<Int> {
        val type = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(data, type)
    }

    /**Convert list to string*/
    @TypeConverter
    fun intListToString(genreIds: List<Int>): String {
        return Gson().toJson(genreIds)
    }
}