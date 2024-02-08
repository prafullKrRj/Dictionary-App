package com.prafullkumar.dictionary.ui.history

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.prafullkumar.dictionary.ui.homeScreen.SuccessScreen

@Composable
fun HistoryDetailsScreen(word: String, viewModel: HistoryViewModel) {
    viewModel.getWordInfo(word)
    val wordDetails by viewModel.wordInfo.collectAsState()
    Text(text = "$wordDetails")
}