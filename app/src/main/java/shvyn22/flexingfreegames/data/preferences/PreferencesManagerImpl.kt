package shvyn22.flexingfreegames.data.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.rxjava3.RxDataStore
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class PreferencesManagerImpl(
    private val dataStore: RxDataStore<Preferences>
) : PreferencesManager {

    override val filterPreferences: Observable<FilterPreferences> = dataStore.data().map { prefs ->
        val platform = prefs[PreferencesKeys.FILTER_PLATFORM] ?: 0
        val sort = prefs[PreferencesKeys.FILTER_SORT] ?: 0
        val category = prefs[PreferencesKeys.FILTER_CATEGORY] ?: 0

        FilterPreferences(
            platform = platform,
            sort = sort,
            category = category
        )
    }.toObservable()

    override fun editFilterPreferences(newFilterValue: FilterPreferences) {
        dataStore.updateDataAsync { prefs ->
            val mutablePrefs = prefs.toMutablePreferences()
            mutablePrefs[PreferencesKeys.FILTER_PLATFORM] = newFilterValue.platform
            mutablePrefs[PreferencesKeys.FILTER_SORT] = newFilterValue.sort
            mutablePrefs[PreferencesKeys.FILTER_CATEGORY] = newFilterValue.category

            Single.just(mutablePrefs)
        }
    }

    private object PreferencesKeys {
        val FILTER_PLATFORM = intPreferencesKey("platform")
        val FILTER_SORT = intPreferencesKey("sort")
        val FILTER_CATEGORY = intPreferencesKey("category")
    }
}