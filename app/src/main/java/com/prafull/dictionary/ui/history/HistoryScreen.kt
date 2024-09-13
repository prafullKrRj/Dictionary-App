package com.prafull.dictionary.ui.history

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.prafull.dictionary.data.local.HistoryEntity
import com.prafull.dictionary.ui.HistoryScreens

@Composable
fun HistoryScreen(viewModel: HistoryViewModel, navController: NavController) {
    val history = viewModel.history.collectAsState()
    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        items(history.value.history.size) { index ->
            HistoryItem(history.value.history[index], index, navController)
        }
    }
}

@Composable
fun HistoryItem(historyEntity: HistoryEntity, index: Int, navController: NavController) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp).clickable {
            navController.navigate(HistoryScreens.DETAILS.name + "/${historyEntity.word}")
        }
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(text = "${index + 1}. ${historyEntity.word}", fontSize = 20.sp)
        }
    }
}