package com.prafullkumar.dictionary.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.prafullkumar.dictionary.R
import com.prafullkumar.dictionary.ui.history.HistoryDetailsScreen
import com.prafullkumar.dictionary.ui.history.HistoryScreen
import com.prafullkumar.dictionary.ui.history.HistoryViewModel
import com.prafullkumar.dictionary.ui.homeScreen.HomeScreen
import com.prafullkumar.dictionary.ui.homeScreen.HomeViewModel
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
fun NavController.goBackStack() {
    if (currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED) {
        popBackStack()
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp() {
    var currentScreen by remember { mutableStateOf(Screens.HOME.name) }
    val navController = rememberNavController()
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
    val homeViewModel: HomeViewModel = viewModel(viewModelStoreOwner)
    val historyViewModel: HistoryViewModel = viewModel(viewModelStoreOwner)
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = if (currentScreen == Screens.HOME.name) "Dictionary App" else "History"
                    )
                },
                navigationIcon = {
                    if (currentScreen == HistoryScreens.DETAILS.name) {
                        IconButton(onClick = {
                            navController.goBackStack()
                        }) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                        }
                    }
                },
            )
        },
        bottomBar = {
            BottomNavigationBar {
                currentScreen = it.name
                navController.goBackStack()
                navController.navigate(it.name)
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            NavHost(navController = navController, startDestination = Screens.HOME.name) {
                composable(Screens.HOME.name) {
                    HomeScreen(viewModel = homeViewModel)
                }
                navigation(route = Screens.HISTORY.name, startDestination = HistoryScreens.MAIN.name) {
                    composable(HistoryScreens.MAIN.name) {
                        currentScreen = Screens.HISTORY.name
                        HistoryScreen(historyViewModel, navController)
                    }
                    composable(HistoryScreens.DETAILS.name + "/{word}") { backStackEntry ->
                        val word = backStackEntry.arguments?.getString("word")
                        if (word != null) {
                            currentScreen = HistoryScreens.DETAILS.name
                            HistoryDetailsScreen(word = word, viewModel = historyViewModel)
                        }
                    }
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
enum class HistoryScreens {
    MAIN, DETAILS
}