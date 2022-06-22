package shvyn22.flexingfreegames.presentation.bookmarks

import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.util.ResourceError

sealed class BookmarksState {
    data class DataState(val data: List<GameModel>): BookmarksState()
    object LoadingState: BookmarksState()
}

sealed class BookmarksEvent {
    data class NavigateToDetailsEvent(val id: Int): BookmarksEvent()
    data class ShowErrorEvent(val error: ResourceError): BookmarksEvent()
}

sealed class BookmarksIntent {
    object LoadBookmarksIntent: BookmarksIntent()
    data class BookmarkClickIntent(val id: Int): BookmarksIntent()
}