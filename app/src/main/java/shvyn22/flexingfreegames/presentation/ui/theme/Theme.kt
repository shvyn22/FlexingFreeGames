package shvyn22.flexingfreegames.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun AppTheme(
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalDimens provides Dimens()) {
        rememberSystemUiController().setStatusBarColor(AppColors.primaryVariant)

        MaterialTheme(
            colors = AppColors,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}