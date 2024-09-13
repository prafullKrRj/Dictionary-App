package com.prafull.dictionary.ui.history

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.prafull.dictionary.data.local.HistoryEntity
import com.prafull.dictionary.ui.commons.ErrorScreen
import com.prafull.dictionary.ui.commons.LoadingScreen
import com.prafull.dictionary.ui.homeScreen.SuccessScreen
import com.prafull.dictionary.utils.Resource

@Composable
fun HistoryDetailsScreen(word: String, viewModel: HistoryViewModel) {
    viewModel.getWordInfo(word)
    val wordDetails by viewModel.wordInfo.collectAsState()
    when (wordDetails) {
        is Resource.Error -> {
            ErrorScreen {
                viewModel.getWordInfo(word)
            }
        }
        Resource.Loading -> {
            LoadingScreen()
        }
        is Resource.Success -> {
            SuccessScreen((wordDetails as Resource.Success<HistoryEntity>).data.wordInfo)
        }
        else -> {
            Text(text = "Initial")
        }
    }
}