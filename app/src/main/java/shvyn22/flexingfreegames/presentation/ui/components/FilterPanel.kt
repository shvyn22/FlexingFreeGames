package shvyn22.flexingfreegames.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.presentation.ui.theme.dimens

@Composable
fun FilterPanel(
    platformIndex: Int,
    onPlatformChange: (Int) -> Unit,
    sortIndex: Int,
    onSortChange: (Int) -> Unit,
    categoryIndex: Int,
    onCategoryChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val orientation = rememberOrientation()

    val isExpanded = remember { mutableStateOf(false) }

    val platforms = stringArrayResource(id = R.array.platforms).toList()
    val sortTypes = stringArrayResource(id = R.array.sort_types).toList()
    val categories = stringArrayResource(id = R.array.categories).toList()

    Card(
        modifier = modifier
            .padding(MaterialTheme.dimens.padding.paddingContentSmall)
    ) {
        Column {
            if (isExpanded.value) {
                when (orientation) {
                    Orientation.PORTRAIT -> {
                        Column {
                            DropdownList(
                                selectedIndex = platformIndex,
                                onSelectedIndexChange = onPlatformChange,
                                items = platforms,
                            )

                            DropdownList(
                                selectedIndex = sortIndex,
                                onSelectedIndexChange = onSortChange,
                                items = sortTypes,
                                modifier = Modifier
                                    .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                            )

                            DropdownList(
                                selectedIndex = categoryIndex,
                                onSelectedIndexChange = onCategoryChange,
                                items = categories,
                                modifier = Modifier
                                    .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                            )
                        }
                    }
                    Orientation.LANDSCAPE -> {
                        Row {
                            DropdownList(
                                selectedIndex = platformIndex,
                                onSelectedIndexChange = onPlatformChange,
                                items = platforms,
                            )

                            DropdownList(
                                selectedIndex = sortIndex,
                                onSelectedIndexChange = onSortChange,
                                items = sortTypes,
                                modifier = Modifier
                                    .padding(start = MaterialTheme.dimens.padding.paddingSmall)
                            )

                            DropdownList(
                                selectedIndex = categoryIndex,
                                onSelectedIndexChange = onCategoryChange,
                                items = categories,
                                modifier = Modifier
                                    .padding(start = MaterialTheme.dimens.padding.paddingSmall)
                            )
                        }
                    }
                }
            }

            val drawableRes =
                if (isExpanded.value) R.drawable.ic_arrow_up
                else R.drawable.ic_arrow_down

            DrawableWrapper(
                drawableStartRes = drawableRes,
                drawableEndRes = drawableRes,
                modifier = Modifier
                    .padding(top = MaterialTheme.dimens.padding.paddingMedium)
                    .clickable {
                        isExpanded.value = !isExpanded.value
                    }
            ) {
                Text(
                    text = stringResource(
                        id = if (isExpanded.value) R.string.text_filters
                        else R.string.text_hide
                    ),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}