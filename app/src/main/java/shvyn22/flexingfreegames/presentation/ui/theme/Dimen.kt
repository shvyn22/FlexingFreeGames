package shvyn22.flexingfreegames.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

object Font {
    val fontSizeBody1 = 20.sp
    val fontSizeBody2 = 14.sp
    val fontSizeCaption = 16.sp
    val letterSpacingBody = 0.5.sp
}

object Shape {
    val smallCornersRadius = 4.dp
    val mediumCornersRadius = 4.dp

    val elevation = 6.dp
}

object Padding {
    val paddingSmall = 6.dp
    val paddingMedium = 8.dp
    val paddingContentSmall = 6.dp
    val paddingContentLarge = 16.dp
}

object Size {
    val widthThumbnail = 163.dp
    val heightThumbnail = 103.dp
    val widthScreenshot = 327.dp
    val heightScreenshot = 184.dp
}

data class Dimens(
    val font: Font = Font,
    val shape: Shape = Shape,
    val padding: Padding = Padding,
    val size: Size = Size,
)

val LocalDimens = compositionLocalOf { Dimens() }

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = LocalDimens.current