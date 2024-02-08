package com.prafullkumar.dictionary.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.prafullkumar.dictionary.domain.models.WordInfoItem

class WordsInfoTypeConverter {

    @TypeConverter
    fun fromWordInfoList(wordInfo: List<WordInfoItem>): String {
        return Gson().toJson(wordInfo)
    }

    @TypeConverter
    fun toWordInfoList(wordInfo: String): List<WordInfoItem> {
        val listType = object : TypeToken<List<WordInfoItem>>() {}.type
        return Gson().fromJson(wordInfo, listType)
    }
}