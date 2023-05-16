package pl.krystiankaniowski.performance.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val NeonColorScheme = darkColorScheme(
    primary = neonGreen,
    onPrimary = Color.Black,
    secondary = neonYellow,
    tertiary = neonBlue,
    background = Color.Black,
    surface = Color.Black,
    error = neonRed,
)

private val DayColorScheme = lightColorScheme(
    primary = hunterGreen,
    secondary = federalBlue,
    tertiary = DeepOrange900,
    background = Color.White,
    surface = Color.White,
    error = chiliRed,
)

@Composable
fun PerformanceTheme(
    darkTheme: Boolean = true,
    // darkTheme: Boolean = isSystemInDarkTheme(),
    // dynamicColor: Boolean = true, // Dynamic color is available on Android 12+
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
        // dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        // val context = LocalContext.current
        // if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        // }
        darkTheme -> NeonColorScheme
        else -> DayColorScheme
    }

    val view = LocalView.current
    val systemUiController = rememberSystemUiController()

    if (!view.isInEditMode) {
        SideEffect {
            systemUiController.setStatusBarColor(
                color = colorScheme.background,
                darkIcons = !darkTheme,
            )
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content,
    )
}