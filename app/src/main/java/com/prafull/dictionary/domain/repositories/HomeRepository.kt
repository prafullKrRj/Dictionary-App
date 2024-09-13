package com.prafull.dictionary.domain.repositories

import com.prafull.dictionary.data.local.HistoryEntity
import com.prafull.dictionary.domain.models.WordInfoItem
import com.prafull.dictionary.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getMeaning(word: String): Flow<Resource<List<WordInfoItem>>>
    suspend fun saveWord(data: HistoryEntity)
}