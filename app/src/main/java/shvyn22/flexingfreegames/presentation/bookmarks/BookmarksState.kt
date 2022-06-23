package shvyn22.flexingfreegames.presentation.bookmarks

import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.util.ResourceError

sealed class BookmarksState {
    data class DataState(val data: List<GameModel>) : BookmarksState()
    object LoadingState : BookmarksState()
    object ErrorState : BookmarksState()
}

sealed class BookmarksEvent {
    data class ShowErrorEvent(val error: ResourceError) : BookmarksEvent()
    data class NavigateToDetailsEvent(val id: Int) : BookmarksEvent()
}

sealed class BookmarksIntent {
    object LoadBookmarksIntent : BookmarksIntent()
    data class BookmarkClickIntent(val id: Int) : BookmarksIntent()
}