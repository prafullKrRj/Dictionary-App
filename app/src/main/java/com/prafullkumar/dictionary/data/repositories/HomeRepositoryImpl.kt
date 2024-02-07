package com.prafullkumar.dictionary.data.repositories

import com.prafullkumar.dictionary.data.remote.DictionaryApiService
import com.prafullkumar.dictionary.domain.models.WordInfo
import com.prafullkumar.dictionary.domain.repositories.HomeRepository
import com.prafullkumar.dictionary.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dictionaryApiService: DictionaryApiService
): HomeRepository {

    override suspend fun getMeaning(word: String): Flow<Resource<WordInfo>> = flow {
        emit(Resource.Loading)
        try {
            val response = dictionaryApiService.getWord(word)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(e))
        }
    }
}