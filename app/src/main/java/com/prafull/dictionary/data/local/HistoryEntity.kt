package com.prafull.dictionary.data.local

import androidx.room.Entity
import com.prafull.dictionary.domain.models.WordInfoItem


@Entity(tableName = "history", primaryKeys = ["word"])
data class HistoryEntity(
    val word: String,
    val wordInfo: List<WordInfoItem>,
    val time: Long
)