package com.prafullkumar.dictionary.data.repositories

import com.prafullkumar.dictionary.data.local.AppDao
import com.prafullkumar.dictionary.data.local.HistoryEntity
import com.prafullkumar.dictionary.domain.repositories.HistoryRepository
import com.prafullkumar.dictionary.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val appDao: AppDao
) : HistoryRepository {

    override fun getHistory(): Flow<List<HistoryEntity>> = appDao.getHistory()
    override suspend fun getWordInfo(word: String): Flow<Resource<HistoryEntity>> = flow {
        emit(Resource.Loading)
        try {
            val wordInfo = appDao.getWordInfo(word)
            emit(Resource.Success(wordInfo))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}