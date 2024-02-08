package com.prafullkumar.dictionary.ui.homeScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafullkumar.dictionary.domain.models.Definition
import com.prafullkumar.dictionary.domain.models.Meaning
import com.prafullkumar.dictionary.domain.models.WordInfoItem
import com.prafullkumar.dictionary.ui.commons.ErrorScreen
import com.prafullkumar.dictionary.ui.commons.Initial
import com.prafullkumar.dictionary.ui.commons.LoadingScreen
import com.prafullkumar.dictionary.ui.commons.SearchBar
import com.prafullkumar.dictionary.utils.Resource

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.wordState.collectAsState()
    var currentQuery by rememberSaveable {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar { query ->
            currentQuery = query
            viewModel.getMeaning(query)
        }
        when (state) {
            is Resource.Error -> {
                ErrorScreen {
                    viewModel.getMeaning(currentQuery)
                }
            }

            Resource.Initial -> {
                Initial()
            }

            Resource.Loading -> {
                LoadingScreen()
            }

            is Resource.Success -> {
                SuccessScreen((state as Resource.Success<List<WordInfoItem>>).data)
            }
        }
    }
}

@Composable
fun SuccessScreen(wordInfo: List<WordInfoItem>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 12.dp), modifier = Modifier.padding(vertical = 8.dp)) {
        item {
            Text(
                modifier = Modifier,
                text = wordInfo?.get(0)?.word?.capitalize() ?: "Sorry",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Start,
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        if (wordInfo.isNotEmpty()) {
            wordInfo[0].meanings.forEachIndexed { index, meaning ->
                item {
                    Text(text = "Meaning ${index + 1}", fontSize = 20.sp)
                    MeaningComp(meaning = meaning)
                }
            }
        }
    }
}

@Composable
fun MeaningComp(meaning: Meaning) {
    ElevatedCard(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
    ) {
        meaning?.partOfSpeech?.let { Text(text = "Part of Speech: ${meaning.partOfSpeech}", modifier = Modifier.padding(8.dp)) }
        if (meaning.definitions.isNotEmpty()) {
            Text(text = "Definitions", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
        meaning.definitions.forEachIndexed { index, definition ->
            DefinitionComp(index, definition)
        }
    }
}

@Composable
fun DefinitionComp(index: Int, definition: Definition) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp), shape = RoundedCornerShape(
            topStart = 8.dp, topEnd = 8.dp, bottomEnd = 0.dp, bottomStart = 0.dp
        )
    ){
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "${index + 1}", modifier = Modifier.weight(.1f))
                Text(text = definition.definition, modifier = Modifier.weight(.9f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Ex: ${definition.example}")
        }
    }
}