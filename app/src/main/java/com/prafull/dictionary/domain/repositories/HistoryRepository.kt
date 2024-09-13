package com.prafull.dictionary.domain.repositories

import com.prafull.dictionary.data.local.HistoryEntity
import com.prafull.dictionary.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {

    fun getHistory(): Flow<List<HistoryEntity>>
    suspend fun getWordInfo(word: String): Flow<Resource<HistoryEntity>>
}