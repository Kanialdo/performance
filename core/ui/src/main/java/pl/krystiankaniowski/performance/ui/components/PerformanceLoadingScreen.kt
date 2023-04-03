package pl.krystiankaniowski.performance.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PerformanceLoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
private fun PerformanceLoadingScreenPreview() {
    PerformanceLoadingScreen()
}

@Composable
fun PerformanceEmptyScreen(
    title: String,
    description: String? = null,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(48.dp),
            painter = rememberVectorPainter(image = Icons.Default.Info),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        description?.let { description ->
            Text(text = description, style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Preview
@Composable
private fun PerformanceEmptyScreenPreview() {
    PerformanceEmptyScreen(
        title = "Title",
        description = "Description",
    )
}