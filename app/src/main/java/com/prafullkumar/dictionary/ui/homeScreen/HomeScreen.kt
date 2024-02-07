package com.prafullkumar.dictionary.ui.homeScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prafullkumar.dictionary.ui.commons.ErrorScreen
import com.prafullkumar.dictionary.ui.commons.LoadingScreen
import com.prafullkumar.dictionary.utils.Resource

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.wordState.collectAsState()


    when (state) {
        is Resource.Error -> {
            ErrorScreen {
                viewModel.getMeaning("hello")
            }
        }
        Resource.Initial -> {
            Text(text = "INITIAL")
            viewModel.getMeaning("hello")
        }
        Resource.Loading -> {
            LoadingScreen()
        }
        is Resource.Success -> {
            Text(text = "Success")
        }
    }
}
