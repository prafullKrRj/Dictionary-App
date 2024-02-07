package com.prafullkumar.dictionary.utils

import com.prafullkumar.dictionary.domain.models.WordInfo

sealed class Resource <out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data object Initial : Resource<Nothing>()
}