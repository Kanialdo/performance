package pl.krystiankaniowski.performance.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun PerformanceTag(name: String, color: Color) {
    Text(modifier = Modifier.background(color = color), text = name)
}