package com.prafullkumar.dictionary.data.local

import androidx.room.Dao
import androidx.room.Upsert
import com.prafullkumar.dictionary.domain.models.WordInfo


@Dao
interface AppDao {
    @Upsert
    fun insertWord(historyEntity: HistoryEntity)
}