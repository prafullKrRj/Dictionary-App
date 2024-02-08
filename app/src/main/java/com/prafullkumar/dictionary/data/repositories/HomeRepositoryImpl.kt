package com.prafullkumar.dictionary.data.repositories

import com.prafullkumar.dictionary.data.local.AppDao
import com.prafullkumar.dictionary.data.local.HistoryEntity
import com.prafullkumar.dictionary.data.remote.DictionaryApiService
import com.prafullkumar.dictionary.domain.models.WordInfo
import com.prafullkumar.dictionary.domain.models.WordInfoItem
import com.prafullkumar.dictionary.domain.repositories.HomeRepository
import com.prafullkumar.dictionary.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dictionaryApiService: DictionaryApiService,
    private val appDao: AppDao
): HomeRepository {

    override suspend fun getMeaning(word: String): Flow<Resource<List<WordInfoItem>>> = flow {
        emit(Resource.Loading)
        try {
            val response = dictionaryApiService.getWord(word)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }

    override suspend fun saveWord(data: HistoryEntity) {
        appDao.insertWord(data)
    }
}