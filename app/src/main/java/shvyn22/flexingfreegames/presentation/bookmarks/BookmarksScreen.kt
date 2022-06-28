package shvyn22.flexingfreegames.presentation.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.presentation.ui.components.items.GameItem
import shvyn22.flexingfreegames.presentation.ui.theme.dimens
import shvyn22.flexingfreegames.util.ResourceError

@Composable
fun BookmarksScreen(
    onShowError: (ResourceError) -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BookmarksViewModel = hiltViewModel()
) {
    val bookmarksState = viewModel.bookmarksState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.bookmarksEvent.collect { event ->
            when (event) {
                is BookmarksEvent.ShowErrorEvent -> onShowError(event.error)
                is BookmarksEvent.NavigateToDetailsEvent -> onNavigateToDetails(event.id)
            }
        }
    }

    bookmarksState.value.let { state ->
        when (state) {
            is BookmarksState.DataState ->
                BookmarksDataContent(
                    bookmarks = state.data,
                    onGameClick = {
                        viewModel.handleIntent(BookmarksIntent.BookmarkClickIntent(it))
                    },
                    modifier = modifier
                )
            is BookmarksState.ErrorState ->
                BookmarksErrorContent(
                    onRetryAction = {
                        viewModel.handleIntent(BookmarksIntent.LoadBookmarksIntent)
                    },
                    modifier = modifier
                )
            is BookmarksState.LoadingState ->
                BookmarksLoadingContent(
                    modifier = modifier
                )
        }
    }
}

@Composable
fun BookmarksDataContent(
    bookmarks: List<GameModel>,
    onGameClick: (Int) -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = modifier
            .padding(horizontal = MaterialTheme.dimens.padding.paddingContentLarge)
    ) {
        LazyColumn {
            items(bookmarks) {
                GameItem(
                    game = it,
                    onGameClick = onGameClick
                )
            }
        }
    }
}

@Composable
fun BookmarksErrorContent(
    onRetryAction: () -> Unit,
    modifier: Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Button(
            onClick = onRetryAction
        ) {
            Text(text = stringResource(id = R.string.text_action_retry))
        }
    }
}

@Composable
fun BookmarksLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator()
    }
}