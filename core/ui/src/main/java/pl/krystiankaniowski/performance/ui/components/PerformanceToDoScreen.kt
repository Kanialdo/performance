package pl.krystiankaniowski.performance.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import pl.krystiankaniowski.performance.ui.R

@Composable
fun PerformanceUnderDevelopmentScreen() {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.laptop))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LottieAnimation(
            modifier = Modifier.size(256.dp),
            composition = composition,
            progress = { progress },
        )
        Text(text = stringResource(R.string.ui_screen_not_ready), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(128.dp))
    }
}

@Preview
@Composable
private fun PerformanceTodoScreenPreview() {
    PerformanceUnderDevelopmentScreen()
}