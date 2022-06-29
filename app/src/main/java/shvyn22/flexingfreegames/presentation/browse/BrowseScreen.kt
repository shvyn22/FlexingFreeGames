package shvyn22.flexingfreegames.presentation.browse

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.presentation.ui.components.items.GameItem
import shvyn22.flexingfreegames.presentation.ui.components.panels.FilterPanel
import shvyn22.flexingfreegames.presentation.ui.theme.dimens
import shvyn22.flexingfreegames.util.ResourceError

@Composable
fun BrowseScreen(
    onShowError: (ResourceError) -> Unit,
    onNavigateToDetails: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BrowseViewModel = hiltViewModel()
) {
    val browseState = viewModel.browseState.collectAsState()

    val platform = remember { mutableStateOf(0) }
    val sort = remember { mutableStateOf(0) }
    val category = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(BrowseIntent.LoadPreferencesIntent)
        viewModel.browseEvent.collect { event ->
            when (event) {
                is BrowseEvent.ShowErrorEvent -> onShowError(event.error)
                is BrowseEvent.UpdateFiltersEvent -> {
                    platform.value = event.platform
                    sort.value = event.sort
                    category.value = event.category
                }
                is BrowseEvent.NavigateToDetailsEvent -> onNavigateToDetails(event.id)
            }
        }
    }

    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            panelFilter,
            content
        ) = createRefs()

        val guidelineStart =
            createGuidelineFromStart(MaterialTheme.dimens.padding.paddingContentLarge)
        val guideLineEnd =
            createGuidelineFromEnd(MaterialTheme.dimens.padding.paddingContentLarge)

        FilterPanel(
            platformIndex = platform.value,
            onPlatformChange = { platform.value = it },
            sortIndex = sort.value,
            onSortChange = { sort.value = it },
            categoryIndex = category.value,
            onCategoryChange = { category.value = it },
            onSearchAction = {
                viewModel.handleIntent(
                    BrowseIntent.LoadGamesIntent(platform.value, sort.value, category.value)
                )
            },
            modifier = Modifier
                .constrainAs(panelFilter) {
                    top.linkTo(parent.top)
                    start.linkTo(guidelineStart)
                    end.linkTo(guideLineEnd)
                    width = Dimension.fillToConstraints
                }
        )

        val contentModifier = Modifier
            .constrainAs(content) {
                top.linkTo(panelFilter.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(guidelineStart)
                end.linkTo(guideLineEnd)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }

        browseState.value.let { state ->
            when (state) {
                is BrowseState.DataState ->
                    BrowseDataContent(
                        games = state.data,
                        onGameClick = {
                            viewModel.handleIntent(BrowseIntent.GameClickIntent(it))
                        },
                        modifier = contentModifier
                    )
                is BrowseState.ErrorState ->
                    BrowseErrorContent(
                        onRetryAction = {
                            viewModel.handleIntent(
                                BrowseIntent.LoadGamesIntent(
                                    platform.value, sort.value, category.value
                                )
                            )
                        },
                        modifier = contentModifier
                    )
                is BrowseState.LoadingState ->
                    BrowseLoadingContent(
                        modifier = contentModifier
                    )
            }
        }
    }
}

@Composable
fun BrowseDataContent(
    games: List<GameModel>,
    onGameClick: (Int) -> Unit,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(games) {
            GameItem(
                game = it,
                onGameClick = onGameClick
            )
        }
    }
}

@Composable
fun BrowseErrorContent(
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
fun BrowseLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}