package pl.krystiankaniowski.performance.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow

@Composable
fun <T> Flow<T>.collectAsEffect(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    block: (T) -> Unit,
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            this@collectAsEffect.collect {
                block(it)
            }
        }
    }
}