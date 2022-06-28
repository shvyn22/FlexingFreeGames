package shvyn22.flexingfreegames.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

enum class Orientation { PORTRAIT, LANDSCAPE }

@Composable
fun rememberOrientation(): Orientation {
    val configuration = LocalConfiguration.current
    return if (configuration.screenWidthDp < 600)
        Orientation.PORTRAIT
    else
        Orientation.LANDSCAPE
}