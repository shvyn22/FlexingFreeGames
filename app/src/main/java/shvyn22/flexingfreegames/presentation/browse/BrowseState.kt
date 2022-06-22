package shvyn22.flexingfreegames.presentation.browse

import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.presentation.bookmarks.BookmarksEvent
import shvyn22.flexingfreegames.util.ResourceError

sealed class BrowseState {
    data class DataState(val data: List<GameModel>) : BrowseState()
    object LoadingState : BrowseState()
}

sealed class BrowseEvent {
    data class ShowErrorEvent(val error: ResourceError): BrowseEvent()
    data class UpdateFiltersEvent(
        val platform: Int,
        val sort: Int,
        val category: Int,
    ) : BrowseEvent()
    data class NavigateToDetailsEvent(val id: Int) : BrowseEvent()
}

sealed class BrowseIntent {
    object LoadPreferencesIntent: BrowseIntent()
    data class LoadGamesIntent(
        val platform: Int,
        val sort: Int,
        val category: Int,
    ): BrowseIntent()
    data class GameClickIntent(val id: Int): BrowseIntent()
}