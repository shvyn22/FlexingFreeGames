package shvyn22.flexingfreegames.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesManagerImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesManager {

    override val filterPreferences: Flow<FilterPreferences> = dataStore.data.map { prefs ->
        val platform = prefs[PreferencesKeys.FILTER_PLATFORM] ?: 0
        val sort = prefs[PreferencesKeys.FILTER_SORT] ?: 0
        val category = prefs[PreferencesKeys.FILTER_CATEGORY] ?: 0

        FilterPreferences(
            platform = platform,
            sort = sort,
            category = category
        )
    }

    override suspend fun editFilterPreferences(newFilterValue: FilterPreferences) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.FILTER_PLATFORM] = newFilterValue.platform
            prefs[PreferencesKeys.FILTER_SORT] = newFilterValue.sort
            prefs[PreferencesKeys.FILTER_CATEGORY] = newFilterValue.category
        }
    }

    private object PreferencesKeys {
        val FILTER_PLATFORM = intPreferencesKey("platform")
        val FILTER_SORT = intPreferencesKey("sort")
        val FILTER_CATEGORY = intPreferencesKey("category")
    }
}