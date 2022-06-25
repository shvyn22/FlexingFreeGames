package shvyn22.flexingfreegames.data.preferences

import io.reactivex.rxjava3.core.Observable

interface PreferencesManager {

    val filterPreferences: Observable<FilterPreferences>

    fun editFilterPreferences(newFilterValue: FilterPreferences)
}