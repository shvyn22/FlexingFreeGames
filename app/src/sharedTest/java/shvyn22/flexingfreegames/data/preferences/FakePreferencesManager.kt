package shvyn22.flexingfreegames.data.preferences

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakePreferencesManager : PreferencesManager {

    private val preferences = MutableStateFlow(FilterPreferences(0, 0, 0))

    override val filterPreferences: Flow<FilterPreferences> = preferences

    override suspend fun editFilterPreferences(newFilterValue: FilterPreferences) {
        preferences.value = newFilterValue
    }
}