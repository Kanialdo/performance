package pl.krystiankaniowski.performance.settings

import dagger.hilt.android.lifecycle.HiltViewModel
import pl.krystiankaniowski.performance.ui.arch.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class SettingsViewModel @Inject constructor(
    @Named("appVersion") private val applicationVersion: String,
) : BaseViewModel<SettingsViewModel.State, Nothing>() {

    override fun initState() = State(
        appVersion = applicationVersion,
    )

    data class State(
        val appVersion: String,
    )
}