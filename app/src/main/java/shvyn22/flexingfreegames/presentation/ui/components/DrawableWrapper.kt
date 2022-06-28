package shvyn22.flexingfreegames.presentation.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun DrawableWrapper(
    modifier: Modifier = Modifier,
    @DrawableRes drawableTopRes: Int? = null,
    @DrawableRes drawableBottomRes: Int? = null,
    @DrawableRes drawableStartRes: Int? = null,
    @DrawableRes drawableEndRes: Int? = null,
    content: @Composable () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (
            drawableTop,
            drawableBottom,
            drawableStart,
            drawableEnd,
            contentRef
        ) = createRefs()

        Box(
            modifier = Modifier
                .constrainAs(contentRef) {
                    top.linkTo(drawableTopRes?.let { drawableTop.bottom } ?: parent.top)
                    bottom.linkTo(drawableBottomRes?.let { drawableBottom.top } ?: parent.bottom)
                    start.linkTo(drawableStartRes?.let { drawableStart.end } ?: parent.start)
                    end.linkTo(drawableEndRes?.let { drawableEnd.start } ?: parent.end)
                }
        ) {
            content()
        }

        drawableTopRes?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(drawableTop) {
                    top.linkTo(parent.top)
                    start.linkTo(contentRef.start)
                    end.linkTo(contentRef.end)
                }
            )
        }

        drawableBottomRes?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(drawableBottom) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(contentRef.start)
                    end.linkTo(contentRef.end)
                }
            )
        }

        drawableStartRes?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(drawableStart) {
                    top.linkTo(contentRef.top)
                    bottom.linkTo(contentRef.bottom)
                    start.linkTo(parent.start)
                }
            )
        }

        drawableEndRes?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                Modifier.constrainAs(drawableEnd) {
                    top.linkTo(contentRef.top)
                    bottom.linkTo(contentRef.bottom)
                    end.linkTo(parent.end)
                }
            )
        }
    }
}