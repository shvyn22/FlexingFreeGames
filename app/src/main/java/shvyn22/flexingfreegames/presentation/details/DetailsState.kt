package shvyn22.flexingfreegames.presentation.details

import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.util.ResourceError

sealed class DetailsState {
    data class DataState(val data: DetailedGameModel, val isBookmarked: Boolean) : DetailsState()
    object LoadingState : DetailsState()
    object ErrorState : DetailsState()
}

sealed class DetailsEvent {
    data class ShowErrorEvent(val error: ResourceError) : DetailsEvent()
    data class NavigateToFreeToGameEvent(val url: String) : DetailsEvent()
    data class NavigateToGameEvent(val url: String) : DetailsEvent()
}

sealed class DetailsIntent {
    data class LoadGameIntent(val id: Int) : DetailsIntent()
    data class InsertBookmarkIntent(val item: DetailedGameModel) : DetailsIntent()
    data class DeleteBookmarkIntent(val id: Int) : DetailsIntent()
    data class FreeToGameIconClickIntent(val url: String) : DetailsIntent()
    data class GameIconClickIntent(val url: String) : DetailsIntent()
}