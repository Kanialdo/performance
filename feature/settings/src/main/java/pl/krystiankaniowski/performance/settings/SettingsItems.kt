package pl.krystiankaniowski.performance.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.krystiankaniowski.performance.domain.settings.SettingsItem
import pl.krystiankaniowski.performance.domain.settings.SettingsItems
import pl.krystiankaniowski.performance.ui.theme.PerformanceTheme

@Composable
internal fun SettingsScreenListItem_Header(
    title: String,
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        },
    )
}

@Composable
internal fun SettingsScreenListItem_Simple(
    item: SettingsItem.Simple,
) {
    ListItem(
        modifier = Modifier.clickable(enabled = item.onClick != null, onClick = { item.onClick?.invoke() }),
        headlineContent = { Text(item.title) },
        supportingContent = item.description?.let { { Text(it) } },
    )
}

@Composable
internal fun SettingsScreenListItem_Switch(
    item: SettingsItem.Switch,
) {
    ListItem(
        headlineContent = { Text(item.title) },
        supportingContent = item.description?.let { { Text(it) } },
        trailingContent = {
            val interactionSource = remember { MutableInteractionSource() }
            Switch(
                checked = item.value,
                onCheckedChange = item.onValueChanged,
                thumbContent = null,
                enabled = item.isEnabled,
                interactionSource = interactionSource,
            )
        },
    )
}

@Preview
@Composable
private fun SettingsScreenListItem_Header_Preview() {
    PerformanceTheme {
        SettingsScreenListItem_Header(
            title = "title",
        )
    }
}

@Preview
@Composable
private fun SettingsScreenListItem_Simple_Preview() {
    PerformanceTheme {
        SettingsScreenListItem_Simple(
            SettingsItem.Simple(
                order = 0,
                category = SettingsItems.Category.ABOUT,
                title = "title",
                description = "description",
            ),
        )
    }
}

@Preview
@Composable
private fun SettingsScreenListItem_Switch_Preview() {
    PerformanceTheme {
        SettingsScreenListItem_Switch(
            SettingsItem.Switch(
                order = 0,
                category = SettingsItems.Category.ABOUT,
                title = "title",
                description = "description",
                value = true,
                isEnabled = true,
                onValueChanged = { },
            ),
        )
    }
}