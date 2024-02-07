package com.prafullkumar.dictionary.data.local

import androidx.room.Entity
import androidx.room.TypeConverters
import com.prafullkumar.dictionary.domain.models.WordInfoItem
import com.prafullkumar.dictionary.utils.WordsInfoTypeConverter


@Entity(tableName = "history", primaryKeys = ["word"])
data class HistoryEntity(
    val word: String,
    @TypeConverters(WordsInfoTypeConverter::class)
    val wordInfo: List<WordInfoItem>
)