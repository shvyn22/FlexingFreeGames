package shvyn22.flexingfreegames.presentation.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.model.DetailedGameModel
import shvyn22.flexingfreegames.presentation.ui.components.RequirementsPanel
import shvyn22.flexingfreegames.presentation.ui.components.ScreenshotItem
import shvyn22.flexingfreegames.presentation.ui.theme.dimens

@Composable
fun DetailsScreen(

) {

}

@ExperimentalPagerApi
@Composable
fun DetailsDataContent(
    game: DetailedGameModel,
    isBookmarked: Boolean,
    onBookmarkIconClick: (Boolean) -> Unit,
    onFreeToGameIconClick: (String) -> Unit,
    onGameIconClick: (String) -> Unit,
    modifier: Modifier,
) {
    val pagerState = rememberPagerState()

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
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
                .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                .constrainAs(textGenre) {
                    top.linkTo(textTitle.bottom)
                    bottom.linkTo(imageThumbnail.bottom)
                    start.linkTo(textTitle.start)
                    end.linkTo(textTitle.end)
                    width = Dimension.fillToConstraints
                }
        )

        Row(
            modifier = Modifier
                .constrainAs(rowIcons) {
                    top.linkTo(barrierHeader)
                    start.linkTo(guidelineStart)
                    end.linkTo(guidelineEnd)
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
                    .clickable { onBookmarkIconClick(!isBookmarked) }
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
                .padding(top = MaterialTheme.dimens.padding.paddingSmall)
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
                .padding(top = MaterialTheme.dimens.padding.paddingSmall)
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
                isLast = index == game.screenshots.lastIndex
            )
        }

        game.systemRequirements?.let { requirements ->
            RequirementsPanel(
                requirements = requirements,
                modifier = Modifier
                    .constrainAs(panelRequirements) {
                        top.linkTo(listScreenshots.bottom)
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
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        Button(
            onClick = onRetryClick
        ) {
            Text(text = stringResource(id = R.string.text_action_retry))
        }
    }
}