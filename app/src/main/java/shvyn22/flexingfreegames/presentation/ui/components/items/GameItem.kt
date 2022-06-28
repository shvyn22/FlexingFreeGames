package shvyn22.flexingfreegames.presentation.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberImagePainter
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.model.GameModel
import shvyn22.flexingfreegames.presentation.ui.components.DrawableWrapper
import shvyn22.flexingfreegames.presentation.ui.theme.dimens

@Composable
fun GameItem(
    game: GameModel,
    onGameClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .padding(MaterialTheme.dimens.padding.paddingSmall)
            .clickable { onGameClick(game.id) }
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(MaterialTheme.dimens.padding.paddingContentSmall)
        ) {
            val (
                imageThumbnail,
                textTitle,
                textPublisher,
                textReleaseDate,
                textShowDescription,
                textDescription,
            ) = createRefs()

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
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
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
                        top.linkTo(parent.top)
                        bottom.linkTo(imageThumbnail.bottom)
                        start.linkTo(imageThumbnail.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = game.publisher,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                    .constrainAs(textPublisher) {
                        top.linkTo(imageThumbnail.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(textReleaseDate.start)
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
                        top.linkTo(imageThumbnail.bottom)
                        start.linkTo(textPublisher.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
            )

            val isExpanded = remember { mutableStateOf(false) }
            val drawableRes =
                if (isExpanded.value) R.drawable.ic_arrow_up
                else R.drawable.ic_arrow_down

            DrawableWrapper(
                drawableStartRes = drawableRes,
                drawableEndRes = drawableRes,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                    .constrainAs(textShowDescription) {
                        top.linkTo(textPublisher.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .clickable {
                        isExpanded.value = !isExpanded.value
                    }
            ) {
                Text(
                    text = stringResource(
                        id = if (isExpanded.value) R.string.text_hide
                        else R.string.text_description
                    ),
                    style = MaterialTheme.typography.caption,
                    textAlign = TextAlign.Center
                )
            }

            if (isExpanded.value) {
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier
                        .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                        .constrainAs(textDescription) {
                            top.linkTo(textShowDescription.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            width = Dimension.fillToConstraints
                        }
                )
            }
        }
    }
}