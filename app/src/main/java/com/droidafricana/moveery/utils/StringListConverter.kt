package com.droidafricana.moveery.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**A [TypeConverter] to help with storing a [List] of Strings with Room*/
class StringListConverter {

    /**Convert string to list*/
    @TypeConverter
    fun stringToList(data: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(data, type)
    }

    /**Convert list to string*/
    fun stringListToString(stringList: List<String>): String {
        return Gson().toJson(stringList)
    }
}