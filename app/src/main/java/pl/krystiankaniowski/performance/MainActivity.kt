package pl.krystiankaniowski.performance

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import pl.krystiankaniowski.performance.notification.StartForegroundServiceUseCase
import pl.krystiankaniowski.performance.notification.StopForegroundServiceUseCase
import pl.krystiankaniowski.performance.settings.SettingsScreen
import pl.krystiankaniowski.performance.stats.StatsScreen
import pl.krystiankaniowski.performance.timer.TimerScreen
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var startForegroundServiceUseCase: StartForegroundServiceUseCase
    @Inject
    lateinit var stopForegroundServiceUseCase: StopForegroundServiceUseCase

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
                        SettingsScreen(
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

    override fun onStart() {
        super.onStart()
        startForegroundServiceUseCase()
    }

    override fun onStop() {
        stopForegroundServiceUseCase()
        super.onStop()
    }
}