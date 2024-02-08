package com.prafullkumar.dictionary.domain.repositories

import com.prafullkumar.dictionary.data.local.HistoryEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getHistory(): Flow<List<HistoryEntity>>
    suspend fun getWordInfo(word: String): Flow<HistoryEntity>
}