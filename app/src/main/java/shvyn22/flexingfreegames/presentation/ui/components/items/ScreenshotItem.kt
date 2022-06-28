package shvyn22.flexingfreegames.presentation.ui.components.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.model.ScreenshotModel
import shvyn22.flexingfreegames.presentation.ui.theme.dimens

@Composable
fun ScreenshotItem(
    screenshot: ScreenshotModel,
    isFirst: Boolean,
    isLast: Boolean,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            startArrow,
            endArrow,
            image
        ) = createRefs()

        if (!isFirst) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_start),
                contentDescription = stringResource(id = R.string.text_accessibility_previous),
                modifier = Modifier
                    .padding(end = MaterialTheme.dimens.padding.paddingSmall)
                    .constrainAs(startArrow) {
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                        start.linkTo(parent.start)
                    }
            )
        }

        if (!isLast) {
            Image(
                painter = painterResource(id = R.drawable.ic_arrow_end),
                contentDescription = stringResource(id = R.string.text_accessibility_next),
                modifier = Modifier
                    .padding(start = MaterialTheme.dimens.padding.paddingSmall)
                    .constrainAs(endArrow) {
                        top.linkTo(image.top)
                        bottom.linkTo(image.bottom)
                        end.linkTo(parent.end)
                    }
            )
        }

        Image(
            painter = rememberImagePainter(
                data = screenshot.image,
                builder = {
                    error(R.drawable.ic_error)
                    crossfade(true)
                }
            ),
            contentDescription = stringResource(id = R.string.text_accessibility_screenshot),
            modifier = Modifier
                .size(
                    width = MaterialTheme.dimens.size.widthScreenshot,
                    height = MaterialTheme.dimens.size.heightScreenshot
                )
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(if (!isFirst) startArrow.end else parent.start)
                    end.linkTo(if (!isLast) endArrow.start else parent.end)
                }
        )
    }
}