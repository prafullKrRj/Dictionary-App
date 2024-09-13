package com.prafull.dictionary.ui.homeScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafull.dictionary.data.local.HistoryEntity
import com.prafull.dictionary.domain.models.WordInfoItem
import com.prafull.dictionary.domain.repositories.HomeRepository
import com.prafull.dictionary.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
):ViewModel() {

    private val _wordState = MutableStateFlow<Resource<List<WordInfoItem>>>(Resource.Initial)
    val wordState = _wordState.asStateFlow()


    fun getMeaning(word: String) {
        viewModelScope.launch {
            homeRepository.getMeaning(word).collect { resp ->
                when (resp) {
                    is Resource.Error -> {
                        _wordState.update {
                            Resource.Error(resp.exception)
                        }
                    }
                    Resource.Initial -> {
                        _wordState.update {
                            Resource.Initial
                        }
                    }
                    Resource.Loading -> {
                        _wordState.update {
                            Resource.Loading
                        }
                    }
                    is Resource.Success -> {
                        _wordState.update {
                            Resource.Success(resp.data)
                        }
                        viewModelScope.launch {
                            withContext(Dispatchers.IO) {
                                homeRepository.saveWord(
                                    HistoryEntity(
                                        word = word,
                                        wordInfo = resp.data.toList(),
                                        time = System.currentTimeMillis()
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}