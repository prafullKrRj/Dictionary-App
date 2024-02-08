package com.prafullkumar.dictionary.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface AppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWord(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Query("SELECT * FROM history WHERE word = :word")
    fun getWordInfo(word: String): HistoryEntity
}