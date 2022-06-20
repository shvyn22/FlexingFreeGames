package shvyn22.flexingfreegames.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import shvyn22.flexingfreegames.util.FILTER_CATEGORY_DEFAULT
import shvyn22.flexingfreegames.util.FILTER_PLATFORM_DEFAULT
import shvyn22.flexingfreegames.util.FILTER_SORT_DEFAULT

class PreferencesManagerImpl(
    private val dataStore: DataStore<Preferences>
) : PreferencesManager {

    override val filterPreferences: Flow<FilterPreferences> = dataStore.data.map { prefs ->
        val platform = prefs[PreferencesKeys.FILTER_PLATFORM] ?: FILTER_PLATFORM_DEFAULT
        val sort = prefs[PreferencesKeys.FILTER_SORT] ?: FILTER_SORT_DEFAULT
        val category = prefs[PreferencesKeys.FILTER_CATEGORY]
            .takeIf { it != FILTER_CATEGORY_DEFAULT }

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
            prefs[PreferencesKeys.FILTER_CATEGORY] =
                newFilterValue.category ?: FILTER_CATEGORY_DEFAULT
        }
    }

    private object PreferencesKeys {
        val FILTER_PLATFORM = stringPreferencesKey("platform")
        val FILTER_SORT = stringPreferencesKey("sort")
        val FILTER_CATEGORY = stringPreferencesKey("category")
    }
}