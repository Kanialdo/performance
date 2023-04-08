package pl.krystiankaniowski.performance.localization.strings

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import pl.krystiankaniowski.performance.domain.localization.strings.StringsProvider
import javax.inject.Inject

class StringsProviderImpl @Inject constructor(@ApplicationContext private val context: Context) : StringsProvider {

    override fun getString(stringId: Int) = context.getString(stringId)
}