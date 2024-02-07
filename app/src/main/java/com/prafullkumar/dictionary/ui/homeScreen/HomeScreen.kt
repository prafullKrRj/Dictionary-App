package com.prafullkumar.dictionary.ui.homeScreen

import androidx.compose.material3.Card
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prafullkumar.dictionary.domain.models.Definition
import com.prafullkumar.dictionary.domain.models.Meaning
import com.prafullkumar.dictionary.domain.models.WordInfo
import com.prafullkumar.dictionary.ui.commons.ErrorScreen
import com.prafullkumar.dictionary.ui.commons.Initial
import com.prafullkumar.dictionary.ui.commons.LoadingScreen
import com.prafullkumar.dictionary.ui.commons.SearchBar
import com.prafullkumar.dictionary.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    val state by viewModel.wordState.collectAsState()
    var currentQuery by rememberSaveable {
        mutableStateOf("")
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text("Dictionary App")
            })
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
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
                    SuccessScreen((state as Resource.Success<WordInfo>).data)
                }
            }
        }
    }
}

@Composable
fun SuccessScreen(wordInfo: WordInfo) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 12.dp)) {
        item {
            Text(
                modifier = Modifier,
                text = wordInfo?.get(0)?.word ?: "Sorry",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Start,
            )
        }
        wordInfo.forEach { wordInfoItem ->
            wordInfoItem.meanings.forEachIndexed { index, meaning ->
                item {
                    Text(text = "Meaning ${index + 1}")
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
        meaning.definitions.forEach { definition ->
            DefinitionComp(definition = definition)
        }
    }
}

@Composable
fun DefinitionComp(definition: Definition) {
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
            Text(text = definition.definition)
            Text(text = "ex: ${definition.example}")
        }
    }
}