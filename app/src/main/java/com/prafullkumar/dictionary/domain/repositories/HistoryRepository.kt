package com.prafullkumar.dictionary.domain.repositories

import com.prafullkumar.dictionary.data.local.HistoryEntity
import com.prafullkumar.dictionary.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getHistory(): Flow<List<HistoryEntity>>
    suspend fun getWordInfo(word: String): Flow<Resource<HistoryEntity>>
}