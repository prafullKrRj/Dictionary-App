package com.prafullkumar.dictionary.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.dictionary.data.local.HistoryEntity
import com.prafullkumar.dictionary.domain.repositories.HistoryRepository
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

    val wordInfo = MutableStateFlow(HistoryEntity("", emptyList()))
    fun getWordInfo(word: String) {
        wordInfo.update {
            HistoryEntity("", emptyList())
        }
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                historyRepository.getWordInfo(word).collect {
                    wordInfo.update {
                        it
                    }
                }
            }
        }
    }
}
data class HistoryState(
    val history: List<HistoryEntity>
)