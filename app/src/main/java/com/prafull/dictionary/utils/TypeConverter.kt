package com.prafull.dictionary.utils

import android.util.Log
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafull.dictionary.domain.models.WordInfoItem

class WordsInfoTypeConverter {

    private val gson = Gson()
    @TypeConverter
    fun fromWordInfoList(wordInfo: List<WordInfoItem>): String {
        val x = gson.toJson(wordInfo)
        Log.d("TypeConverter", "fromWordInfoList: $x")
        return gson.toJson(wordInfo)
    }

    @TypeConverter
    fun toWordInfoList(wordInfo: String): List<WordInfoItem> {
        val x: List<WordInfoItem> = gson.fromJson(wordInfo, object : TypeToken<List<WordInfoItem>>() {}.type)
        Log.d("TypeConverter", "toWordInfoList: $x")
        return x
    }
}
