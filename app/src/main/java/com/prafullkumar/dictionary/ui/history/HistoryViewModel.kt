package com.prafullkumar.dictionary.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.dictionary.data.local.HistoryEntity
import com.prafullkumar.dictionary.domain.repositories.HistoryRepository
import com.prafullkumar.dictionary.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: HistoryRepository
): ViewModel() {

    val history: StateFlow<HistoryState> = historyRepository.getHistory().map {
        HistoryState(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HistoryState(emptyList()))

    val wordInfo = MutableStateFlow<Resource<HistoryEntity>>(Resource.Initial)
    fun getWordInfo(word: String) {
        wordInfo.update {
            Resource.Loading
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                historyRepository.getWordInfo(word).collect { resp ->
                    when (resp) {
                        is Resource.Error -> {
                            wordInfo.update {
                                Resource.Error(resp.exception)
                            }
                        }
                        Resource.Loading -> {
                            wordInfo.update {
                                Resource.Loading
                            }
                        }
                        is Resource.Success -> {
                            wordInfo.update {
                                Resource.Success(resp.data)
                            }
                        } else -> {
                            wordInfo.update {
                                Resource.Initial
                            }
                        }
                    }
                }
            }
        }
    }
}
data class HistoryState(
    val history: List<HistoryEntity>
)