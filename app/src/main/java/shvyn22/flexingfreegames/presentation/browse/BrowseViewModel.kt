package shvyn22.flexingfreegames.presentation.browse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.PublishSubject
import shvyn22.flexingfreegames.data.preferences.FilterPreferences
import shvyn22.flexingfreegames.data.preferences.PreferencesManager
import shvyn22.flexingfreegames.repository.remote.RemoteRepository
import shvyn22.flexingfreegames.util.*
import javax.inject.Inject

class BrowseViewModel @Inject constructor(
    private val preferences: PreferencesManager,
    private val remoteRepo: RemoteRepository,
) : ViewModel() {

    private val _browseState = MutableLiveData<BrowseState>(BrowseState.LoadingState)
    val browseState: LiveData<BrowseState> get() = _browseState

    private val _browseEvent = PublishSubject.create<BrowseEvent>()
    val browseEvent: Observable<BrowseEvent>
        get() = _browseEvent.flatMap { Observable.just(it) }

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
        preferences
            .filterPreferences
            .subscribeOn(Schedulers.io())
            .subscribe { prefs ->
                emitUpdateFiltersEvent(prefs.platform, prefs.sort, prefs.category)
            }
    }

    private fun loadGames(
        platform: Int,
        sort: Int,
        category: Int
    ) {
        if (platform + sort + category != 0)
            preferences.editFilterPreferences(FilterPreferences(platform, sort, category))
        remoteRepo
            .getGames(
                PLATFORMS[platform],
                SORT_TYPES[sort],
                CATEGORIES[category]
            )
            .subscribeOn(Schedulers.io())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Success ->
                        _browseState.postValue(BrowseState.DataState(resource.data))
                    is Resource.Loading ->
                        _browseState.postValue(BrowseState.LoadingState)
                    is Resource.Error -> {
                        _browseState.postValue(BrowseState.ErrorState)
                        emitShowErrorEvent(resource.error)
                    }
                }
            }
    }

    private fun emitShowErrorEvent(error: ResourceError) {
        _browseEvent.onNext(BrowseEvent.ShowErrorEvent(error))
    }

    private fun emitUpdateFiltersEvent(
        platform: Int,
        sort: Int,
        category: Int,
    ) {
        _browseEvent.onNext(
            BrowseEvent.UpdateFiltersEvent(
                platform = platform,
                sort = sort,
                category = category
            )
        )
    }

    private fun emitNavigateEvent(id: Int) {
        _browseEvent.onNext(BrowseEvent.NavigateToDetailsEvent(id))
    }
}