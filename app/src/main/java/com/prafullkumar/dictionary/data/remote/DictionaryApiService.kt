package com.prafullkumar.dictionary.data.remote

import com.prafullkumar.dictionary.domain.models.WordInfoItem
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryApiService {
    @GET("/api/v2/entries/en/{word}")
    suspend fun getWord(
        @Path("word") word: String
    ) : List<WordInfoItem>
}