package shvyn22.flexingfreegames.presentation.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import shvyn22.flexingfreegames.repository.local.LocalRepository
import shvyn22.flexingfreegames.util.Resource
import shvyn22.flexingfreegames.util.ResourceError
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val localRepo: LocalRepository,
) : ViewModel() {

    private val _bookmarksState = MutableStateFlow<BookmarksState>(BookmarksState.LoadingState)
    val bookmarksState get() = _bookmarksState.asStateFlow()

    private val _bookmarksEvent = MutableSharedFlow<BookmarksEvent>()
    val bookmarksEvent get() = _bookmarksEvent.asSharedFlow()

    init {
        handleIntent(BookmarksIntent.LoadBookmarksIntent)
    }

    fun handleIntent(intent: BookmarksIntent) {
        when (intent) {
            is BookmarksIntent.LoadBookmarksIntent -> loadBookmarks()
            is BookmarksIntent.BookmarkClickIntent -> emitNavigateEvent(intent.id)
        }
    }

    private fun loadBookmarks() {
        viewModelScope.launch {
            localRepo.getBookmarks().collect { resource ->
                when (resource) {
                    is Resource.Success ->
                        _bookmarksState.value = BookmarksState.DataState(resource.data)
                    is Resource.Loading ->
                        _bookmarksState.value = BookmarksState.LoadingState
                    is Resource.Error -> {
                        _bookmarksState.value = BookmarksState.ErrorState
                        emitShowErrorEvent(resource.error)
                    }
                }
            }
        }
    }

    private fun emitShowErrorEvent(error: ResourceError) {
        viewModelScope.launch {
            _bookmarksEvent.emit(BookmarksEvent.ShowErrorEvent(error))
        }
    }

    private fun emitNavigateEvent(id: Int) {
        viewModelScope.launch {
            _bookmarksEvent.emit(BookmarksEvent.NavigateToDetailsEvent(id))
        }
    }
}