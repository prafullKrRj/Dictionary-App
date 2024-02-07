package com.prafullkumar.dictionary.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafullkumar.dictionary.domain.models.WordInfoItem

class WordsInfoTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun fromWordInfo(wordInfo: List<WordInfoItem>): String {
        return gson.toJson(wordInfo)
    }

    @TypeConverter
    fun toWordInfo(data: String): List<WordInfoItem> {
        val listType = object : TypeToken<List<WordInfoItem>>() {}.type
        return gson.fromJson(data, listType)
    }
}