package com.prafullkumar.dictionary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.dictionary.R
import com.prafullkumar.dictionary.ui.homeScreen.HomeScreen
import com.prafullkumar.dictionary.ui.theme.DictionaryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DictionaryTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf(Screens.HOME) }
    val navController = rememberNavController()
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = {
                Text(
                    text = if (currentScreen == Screens.HOME) "Dictionary" else "History")
            })
        },
        bottomBar = {
            BottomNavigationBar {
                currentScreen = it
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavHost(navController = navController, startDestination = Screens.HOME.name) {
                composable(Screens.HOME.name) {
                    HomeScreen()
                }
                composable(Screens.HISTORY.name) {
                    Text(text = "History")
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(onNavigation: (Screens) -> Unit) {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf(
        "Home" to R.drawable.baseline_home_24, "History" to R.drawable.baseline_history_24
    )
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(painter = painterResource(id = item.second), contentDescription = null) },
                label = { Text(item.first) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    onNavigation(
                        when (index) {
                            0 -> Screens.HOME
                            1 -> Screens.HISTORY
                            else -> Screens.HOME
                        }
                    )
                }
            )
        }
    }
}
enum class Screens {
    HOME, HISTORY
}