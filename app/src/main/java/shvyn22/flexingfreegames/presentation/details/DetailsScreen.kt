package shvyn22.flexingfreegames.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.presentation.ui.components.items.ScreenshotItem
import shvyn22.flexingfreegames.presentation.ui.components.panels.RequirementsPanel
import shvyn22.flexingfreegames.presentation.ui.theme.dimens
import shvyn22.flexingfreegames.util.ResourceError

@ExperimentalPagerApi
@Composable
fun DetailsScreen(
    id: Int,
    onShowError: (ResourceError) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(id) {
        viewModel.handleIntent(DetailsIntent.LoadGameIntent(id))
    }

    val context = LocalContext.current

    val detailsState = viewModel.detailsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.detailsEvent.collect { event ->
            when (event) {
                is DetailsEvent.ShowErrorEvent -> onShowError(event.error)
                is DetailsEvent.NavigateToFreeToGameEvent -> {
                    Intent(Intent.ACTION_VIEW, Uri.parse(event.url)).also {
                        startActivity(context, it, null)
                    }
                }
                is DetailsEvent.NavigateToGameEvent -> {
                    Intent(Intent.ACTION_VIEW, Uri.parse(event.url)).also {
                        startActivity(context, it, null)
                    }
                }
            }
        }
    }

    detailsState.value.let { state ->
        when (state) {
            is DetailsState.DataState ->
                DetailsDataContent(
                    game = state.data,
                    isBookmarked = state.isBookmarked,
                    onInsertBookmark = {
                        viewModel.handleIntent(DetailsIntent.InsertBookmarkIntent(it))
                    },
                    onDeleteBookmark = {
                        viewModel.handleIntent(DetailsIntent.DeleteBookmarkIntent(it))
                    },
                    onFreeToGameIconClick = {
                        viewModel.handleIntent(DetailsIntent.FreeToGameIconClickIntent(it))
                    },
                    onGameIconClick = {
                        viewModel.handleIntent(DetailsIntent.GameIconClickIntent(it))
                    },
                    modifier = modifier
                )
            is DetailsState.ErrorState ->
                DetailsErrorContent(
                    onRetryAction = {
                        viewModel.handleIntent(DetailsIntent.LoadGameIntent(id))
                    },
                    modifier = modifier
                )
            is DetailsState.LoadingState ->
                DetailsLoadingContent(
                    modifier = modifier
                )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun DetailsDataContent(
    game: DetailedGameModel,
    isBookmarked: Boolean,
    onInsertBookmark: (DetailedGameModel) -> Unit,
    onDeleteBookmark: (Int) -> Unit,
    onFreeToGameIconClick: (String) -> Unit,
    onGameIconClick: (String) -> Unit,
    modifier: Modifier,
) {
    val pagerState = rememberPagerState()
    val scrollState = rememberScrollState()

    ConstraintLayout(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        val (
            imageThumbnail,
            textTitle,
            textGenre,
            rowIcons,
            textPublisher,
            textDeveloper,
            textReleaseDate,
            textPlatform,
            textDescription,
            listScreenshots,
            panelRequirements
        ) = createRefs()

        val guidelineTop = createGuidelineFromTop(MaterialTheme.dimens.padding.paddingContentLarge)
        val guidelineBottom =
            createGuidelineFromBottom(MaterialTheme.dimens.padding.paddingContentLarge)
        val guidelineStart =
            createGuidelineFromStart(MaterialTheme.dimens.padding.paddingContentLarge)
        val guidelineEnd = createGuidelineFromEnd(MaterialTheme.dimens.padding.paddingContentLarge)
        val barrierHeader = createBottomBarrier(imageThumbnail, textTitle, textGenre)
        val barrierInfo = createBottomBarrier(textDeveloper, textPlatform)

        Image(
            painter = rememberImagePainter(
                data = game.thumbnail,
                builder = {
                    error(R.drawable.ic_error)
                    crossfade(true)
                }
            ),
            contentDescription = stringResource(id = R.string.text_accessibility_thumb),
            modifier = Modifier
                .size(
                    width = MaterialTheme.dimens.size.widthThumbnail,
                    height = MaterialTheme.dimens.size.heightThumbnail
                )
                .constrainAs(imageThumbnail) {
                    top.linkTo(guidelineTop)
                    start.linkTo(guidelineStart)
                    end.linkTo(textTitle.start)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = game.title,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(start = MaterialTheme.dimens.padding.paddingSmall)
                .constrainAs(textTitle) {
                    top.linkTo(guidelineTop)
                    bottom.linkTo(textGenre.top)
                    start.linkTo(imageThumbnail.end)
                    end.linkTo(guidelineEnd)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = game.genre,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .constrainAs(textGenre) {
                    top.linkTo(textTitle.bottom)
                    bottom.linkTo(imageThumbnail.bottom)
                    start.linkTo(textTitle.start)
                    end.linkTo(textTitle.end)
                    width = Dimension.fillToConstraints
                }
                .padding(
                    top = MaterialTheme.dimens.padding.paddingSmall,
                    start = MaterialTheme.dimens.padding.paddingSmall
                )
        )

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.padding.paddingMedium)
                .constrainAs(rowIcons) {
                    top.linkTo(barrierHeader)
                    start.linkTo(guidelineStart)
                    end.linkTo(guidelineEnd)
                    width = Dimension.fillToConstraints
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_freetogame),
                contentDescription = stringResource(id = R.string.text_accessibility_freetogame),
                modifier = Modifier
                    .clickable { onFreeToGameIconClick(game.freeToGameUrl) }
            )

            Image(
                painter = painterResource(
                    id = if (isBookmarked) R.drawable.ic_bookmarked
                    else R.drawable.ic_not_bookmarked
                ),
                contentDescription = stringResource(id = R.string.text_accessibility_bookmark),
                modifier = Modifier
                    .clickable {
                        if (isBookmarked) onDeleteBookmark(game.id)
                        else onInsertBookmark(game)
                    }
                    .padding(horizontal = MaterialTheme.dimens.padding.paddingMedium)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_web),
                contentDescription = stringResource(id = R.string.text_accessibility_game_url),
                modifier = Modifier
                    .clickable { onGameIconClick(game.gameUrl) }
            )
        }

        Text(
            text = game.publisher,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.padding.paddingMedium)
                .constrainAs(textPublisher) {
                    top.linkTo(rowIcons.bottom)
                    start.linkTo(guidelineStart)
                    end.linkTo(textReleaseDate.start)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = game.developer,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                .constrainAs(textDeveloper) {
                    top.linkTo(textPublisher.bottom)
                    start.linkTo(guidelineStart)
                    end.linkTo(textPlatform.start)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = game.releaseDate,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.padding.paddingMedium)
                .constrainAs(textReleaseDate) {
                    top.linkTo(rowIcons.bottom)
                    start.linkTo(textPublisher.end)
                    end.linkTo(guidelineEnd)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = game.platform,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                .constrainAs(textPlatform) {
                    top.linkTo(textReleaseDate.bottom)
                    start.linkTo(textDeveloper.end)
                    end.linkTo(guidelineEnd)
                    width = Dimension.fillToConstraints
                }
        )

        Text(
            text = game.detailedDescription,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                .constrainAs(textDescription) {
                    top.linkTo(barrierInfo)
                    start.linkTo(guidelineStart)
                    end.linkTo(guidelineEnd)
                    width = Dimension.fillToConstraints
                }
        )

        HorizontalPager(
            state = pagerState,
            count = game.screenshots.size,
            modifier = Modifier
                .padding(top = MaterialTheme.dimens.padding.paddingMedium)
                .constrainAs(listScreenshots) {
                    top.linkTo(textDescription.bottom)
                    start.linkTo(guidelineStart)
                    end.linkTo(guidelineEnd)
                    width = Dimension.fillToConstraints
                }
        ) { index ->
            ScreenshotItem(
                screenshot = game.screenshots[index],
                isFirst = index == 0,
                isLast = index == game.screenshots.lastIndex,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        game.systemRequirements?.let { requirements ->
            RequirementsPanel(
                requirements = requirements,
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.padding.paddingMedium)
                    .constrainAs(panelRequirements) {
                        top.linkTo(listScreenshots.bottom)
                        bottom.linkTo(guidelineBottom)
                        start.linkTo(guidelineStart)
                        end.linkTo(guidelineEnd)
                        width = Dimension.fillToConstraints
                    }

            )
        }
    }
}

@Composable
fun DetailsErrorContent(
    onRetryAction: () -> Unit,
    modifier: Modifier = Modifier
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
fun DetailsLoadingContent(
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}