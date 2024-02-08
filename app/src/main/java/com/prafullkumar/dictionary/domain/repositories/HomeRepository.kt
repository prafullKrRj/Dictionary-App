package com.prafullkumar.dictionary.domain.repositories

import com.prafullkumar.dictionary.data.local.HistoryEntity
import com.prafullkumar.dictionary.domain.models.WordInfo
import com.prafullkumar.dictionary.domain.models.WordInfoItem
import com.prafullkumar.dictionary.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getMeaning(word: String): Flow<Resource<List<WordInfoItem>>>
    suspend fun saveWord(data: HistoryEntity)
}