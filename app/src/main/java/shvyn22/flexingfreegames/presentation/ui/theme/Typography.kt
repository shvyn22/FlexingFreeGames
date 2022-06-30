package shvyn22.flexingfreegames.presentation.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

val AppTypography = Typography(
    body1 = TextStyle(
        fontSize = Font.fontSizeBody1,
        fontWeight = FontWeight.Bold,
        letterSpacing = Font.letterSpacingBody,
    ),
    body2 = TextStyle(
        fontSize = Font.fontSizeBody2,
        fontWeight = FontWeight.Bold,
        letterSpacing = Font.letterSpacingBody,
    ),
    caption = TextStyle(
        color = Color.White,
        fontSize = Font.fontSizeCaption,
        fontWeight = FontWeight.Bold,
    )
)