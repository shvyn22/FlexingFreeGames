package shvyn22.flexingfreegames.presentation.browse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shvyn22.flexingfreegames.data.preferences.FilterPreferences
import shvyn22.flexingfreegames.data.preferences.PreferencesManager
import shvyn22.flexingfreegames.repository.remote.RemoteRepository
import shvyn22.flexingfreegames.util.*
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val preferences: PreferencesManager,
    private val remoteRepo: RemoteRepository,
) : ViewModel() {

    private val _browseState = MutableStateFlow<BrowseState>(BrowseState.LoadingState)
    val browseState get() = _browseState.asStateFlow()

    private val _browseEvent = MutableSharedFlow<BrowseEvent>()
    val browseEvent get() = _browseEvent.asSharedFlow()

    init {
        handleIntent(BrowseIntent.LoadPreferencesIntent)
        handleIntent(BrowseIntent.LoadGamesIntent(0, 0, 0))
    }

    fun handleIntent(intent: BrowseIntent) {
        when (intent) {
            is BrowseIntent.LoadPreferencesIntent ->
                loadPreferences()
            is BrowseIntent.LoadGamesIntent ->
                loadGames(intent.platform, intent.sort, intent.category)
            is BrowseIntent.GameClickIntent ->
                emitNavigateEvent(intent.id)
        }
    }

    private fun loadPreferences() {
        viewModelScope.launch {
            preferences.filterPreferences.collect { prefs ->
                emitUpdateFiltersEvent(prefs.platform, prefs.sort, prefs.category)
            }
        }
    }

    private fun loadGames(
        platform: Int,
        sort: Int,
        category: Int
    ) {
        viewModelScope.launch {
            if (platform + sort + category != 0)
                preferences.editFilterPreferences(FilterPreferences(platform, sort, category))
            remoteRepo
                .getGames(
                    PLATFORMS[platform],
                    SORT_TYPES[sort],
                    CATEGORIES[category]
                )
                .collect { resource ->
                    when (resource) {
                        is Resource.Success ->
                            _browseState.value = BrowseState.DataState(resource.data)
                        is Resource.Loading ->
                            _browseState.value = BrowseState.LoadingState
                        is Resource.Error -> {
                            _browseState.value = BrowseState.ErrorState
                            emitShowErrorEvent(resource.error)
                        }
                    }
                }
        }
    }

    private fun emitShowErrorEvent(error: ResourceError) {
        viewModelScope.launch {
            _browseEvent.emit(BrowseEvent.ShowErrorEvent(error))
        }
    }

    private fun emitUpdateFiltersEvent(
        platform: Int,
        sort: Int,
        category: Int,
    ) {
        viewModelScope.launch {
            _browseEvent.emit(
                BrowseEvent.UpdateFiltersEvent(
                    platform = platform,
                    sort = sort,
                    category = category
                )
            )
        }
    }

    private fun emitNavigateEvent(id: Int) {
        viewModelScope.launch {
            _browseEvent.emit(BrowseEvent.NavigateToDetailsEvent(id))
        }
    }
}