package com.prafullkumar.dictionary.ui.commons

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(onSearch: (String) -> Unit = { }) {
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    SearchBar(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp),
        query = searchQuery,
        onQueryChange = { searchQuery = it },
        onSearch = {
            if (searchQuery.isNotEmpty()) {
                keyBoardController?.hide()
                onSearch(searchQuery)
            } else {
                Toast.makeText(
                    context,
                    "Please enter a word to search",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        active = false,
        onActiveChange = {},
        placeholder = { Text("word") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (searchQuery.isNotEmpty()) {
                        keyBoardController?.hide()
                        onSearch(searchQuery)
                    }
                }
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
            }
        }
    ) {}
}