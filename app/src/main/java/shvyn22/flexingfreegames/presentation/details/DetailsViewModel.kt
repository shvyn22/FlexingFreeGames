package shvyn22.flexingfreegames.presentation.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.repository.local.LocalRepository
import shvyn22.flexingfreegames.repository.remote.RemoteRepository
import shvyn22.flexingfreegames.util.Resource
import shvyn22.flexingfreegames.util.ResourceError
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val remoteRepo: RemoteRepository,
    private val localRepo: LocalRepository
) : ViewModel() {

    private val _detailsState = MutableStateFlow<DetailsState>(DetailsState.LoadingState)
    val detailsState get() = _detailsState.asStateFlow()

    private val _detailsEvent = MutableSharedFlow<DetailsEvent>()
    val detailsEvent get() = _detailsEvent.asSharedFlow()

    fun handleIntent(intent: DetailsIntent) {
        when (intent) {
            is DetailsIntent.LoadGameIntent ->
                loadGame(intent.id)
            is DetailsIntent.InsertBookmarkIntent ->
                insertBookmark(intent.item)
            is DetailsIntent.DeleteBookmarkIntent ->
                deleteBookmark(intent.id)
            is DetailsIntent.FreeToGameIconClickIntent ->
                emitNavigateToFreeToGameEvent(intent.url)
            is DetailsIntent.GameIconClickIntent ->
                emitNavigateToGameEvent(intent.url)
        }
    }

    private fun loadGame(id: Int) {
        viewModelScope.launch {
            remoteRepo.getGameDetails(id).collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val isBookmarked = localRepo.isGameBookmarked(resource.data.id)
                        _detailsState.value =
                            DetailsState.DataState(resource.data, isBookmarked)
                    }
                    is Resource.Loading ->
                        _detailsState.value = DetailsState.LoadingState
                    is Resource.Error -> {
                        _detailsState.value = DetailsState.ErrorState
                        emitShowErrorEvent(resource.error)
                    }
                }
            }
        }
    }

    private fun insertBookmark(item: DetailedGameModel) {
        viewModelScope.launch { localRepo.insertBookmark(item) }
        toggleBookmarkIcon()
    }

    private fun deleteBookmark(id: Int) {
        viewModelScope.launch { localRepo.deleteBookmark(id) }
        toggleBookmarkIcon()
    }

    private fun toggleBookmarkIcon() {
        viewModelScope.launch {
            _detailsState.value.let { state ->
                if (state is DetailsState.DataState)
                    state.copy(isFavorite = !state.isFavorite).also {
                        _detailsState.value = it
                    }
            }
        }
    }

    private fun emitShowErrorEvent(error: ResourceError) {
        viewModelScope.launch {
            _detailsEvent.emit(DetailsEvent.ShowErrorEvent(error))
        }
    }

    private fun emitNavigateToFreeToGameEvent(url: String) {
        viewModelScope.launch {
            _detailsEvent.emit(DetailsEvent.NavigateToFreeToGameEvent(url))
        }
    }

    private fun emitNavigateToGameEvent(url: String) {
        viewModelScope.launch {
            _detailsEvent.emit(DetailsEvent.NavigateToGameEvent(url))
        }
    }
}