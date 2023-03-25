package pl.krystiankaniowski.performance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.krystiankaniowski.performance.settings.SettingsScreen2
import pl.krystiankaniowski.performance.stats.StatsScreen
import pl.krystiankaniowski.performance.timer.TimerScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PerformanceTheme {
                val navController = rememberNavController()
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
                        SettingsScreen2(
                            navigateUp = navController::navigateUp,
                        )
                    }
                    composable("stats") {
                        StatsScreen(
                            navigateUp = navController::navigateUp,
                        )
                    }
                }
            }
        }
    }
}