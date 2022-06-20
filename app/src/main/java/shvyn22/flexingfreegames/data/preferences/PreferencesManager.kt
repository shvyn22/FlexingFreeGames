package shvyn22.flexingfreegames.data.preferences

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {

    val filterPreferences: Flow<FilterPreferences>

    suspend fun editFilterPreferences(newFilterValue: FilterPreferences)
}