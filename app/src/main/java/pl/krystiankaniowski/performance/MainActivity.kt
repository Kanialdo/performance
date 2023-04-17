package pl.krystiankaniowski.performance

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pl.krystiankaniowski.performance.addhistory.AddDetailsScreen
import pl.krystiankaniowski.performance.historydetails.HistoryDetailsArgs
import pl.krystiankaniowski.performance.historydetails.HistoryDetailsScreen
import pl.krystiankaniowski.performance.navigation.AndroidNavigator
import pl.krystiankaniowski.performance.settings.SettingsScreen
import pl.krystiankaniowski.performance.stats.StatsScreen
import pl.krystiankaniowski.performance.timer.TimerScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    @Inject
    lateinit var androidNavigator: AndroidNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        androidNavigator.activity = this
        setContent {
            PerformanceTheme {
                val navController = rememberNavController()
                DisposableEffect(navController) {
                    navController.addOnDestinationChangedListener(viewModel)
                    onDispose {
                        navController.removeOnDestinationChangedListener(viewModel)
                    }
                }
                NavHost(navController = navController, startDestination = "timer") {
                    composable("timer") {
                        TimerScreen(
                            onNavigateToSettings = {
                                navController.navigate("settings")
                            },
                            onNavigateToStats = {
                                navController.navigate("stats")
                            },
                        )
                    }
                    composable("settings") {
                        SettingsScreen(
                            navigateUp = navController::navigateUp,
                        )
                    }
                    composable("stats") {
                        StatsScreen(
                            navigateUp = navController::navigateUp,
                            openAddItemScreen = { navController.navigate("details-add") },
                            openDetailsScreen = { id -> navController.navigate("details/$id") },
                        )
                    }
                    composable(route = "details-add}") {
                        AddDetailsScreen(
                            navigateUp = navController::navigateUp,
                        )
                    }
                    composable(
                        route = "details/{${HistoryDetailsArgs.id}}",
                        arguments = listOf(
                            navArgument(HistoryDetailsArgs.id) { type = NavType.LongType },
                        ),
                    ) {
                        HistoryDetailsScreen(
                            navigateUp = navController::navigateUp,
                        )
                    }
                }
            }
        }

        lifecycleScope.launch {
            viewModel.state.map { it.keepAwake }.distinctUntilChanged().collect { keepAwake ->
                if (keepAwake) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                } else {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
                }
            }
        }
    }

    override fun onDestroy() {
        androidNavigator.activity = null
        super.onDestroy()
    }
}