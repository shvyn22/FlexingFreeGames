package shvyn22.flexingfreegames.presentation.ui.components.panels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.presentation.ui.components.DrawableWrapper
import shvyn22.flexingfreegames.presentation.ui.components.DropdownList
import shvyn22.flexingfreegames.presentation.ui.theme.dimens
import shvyn22.flexingfreegames.util.Orientation
import shvyn22.flexingfreegames.util.rememberOrientation

@Composable
fun FilterPanel(
    platformIndex: Int,
    onPlatformChange: (Int) -> Unit,
    sortIndex: Int,
    onSortChange: (Int) -> Unit,
    categoryIndex: Int,
    onCategoryChange: (Int) -> Unit,
    onSearchAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    val orientation = rememberOrientation()
    val isExpanded = remember { mutableStateOf(false) }

    val platforms = stringArrayResource(id = R.array.platforms).toList()
    val sortTypes = stringArrayResource(id = R.array.sort_types).toList()
    val categories = stringArrayResource(id = R.array.categories).toList()

    Card(
        elevation = MaterialTheme.dimens.shape.elevation,
        modifier = modifier,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(MaterialTheme.dimens.padding.paddingContentSmall)
        ) {
            if (isExpanded.value) {
                when (orientation) {
                    Orientation.PORTRAIT -> {
                        Column {
                            DropdownList(
                                selectedIndex = platformIndex,
                                onSelectedIndexChange = onPlatformChange,
                                items = platforms,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )

                            DropdownList(
                                selectedIndex = sortIndex,
                                onSelectedIndexChange = onSortChange,
                                items = sortTypes,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                            )

                            DropdownList(
                                selectedIndex = categoryIndex,
                                onSelectedIndexChange = onCategoryChange,
                                items = categories,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = MaterialTheme.dimens.padding.paddingSmall)
                            )
                        }
                    }
                    Orientation.LANDSCAPE -> {
                        Row(
                            horizontalArrangement = Arrangement.SpaceAround,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                DropdownList(
                                    selectedIndex = platformIndex,
                                    onSelectedIndexChange = onPlatformChange,
                                    items = platforms,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = MaterialTheme.dimens.padding.paddingSmall
                                        )
                                        .fillMaxWidth()
                                )
                            }

                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                DropdownList(
                                    selectedIndex = sortIndex,
                                    onSelectedIndexChange = onSortChange,
                                    items = sortTypes,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = MaterialTheme.dimens.padding.paddingSmall
                                        )
                                        .fillMaxWidth()
                                )
                            }

                            Box(
                                modifier = Modifier.weight(1f)
                            ) {
                                DropdownList(
                                    selectedIndex = categoryIndex,
                                    onSelectedIndexChange = onCategoryChange,
                                    items = categories,
                                    modifier = Modifier
                                        .padding(
                                            horizontal = MaterialTheme.dimens.padding.paddingSmall
                                        )
                                        .fillMaxWidth()
                                )
                            }
                        }
                    }
                }

                Button(
                    onClick = onSearchAction
                ) {
                    Text(text = stringResource(id = R.string.text_action_search))
                }
            }

            val drawableRes =
                if (isExpanded.value) R.drawable.ic_arrow_up
                else R.drawable.ic_arrow_down

            DrawableWrapper(
                drawableStartRes = drawableRes,
                drawableEndRes = drawableRes,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.dimens.padding.paddingMedium)
                    .clickable {
                        isExpanded.value = !isExpanded.value
                    }
            ) {
                Text(
                    text = stringResource(
                        id = if (isExpanded.value) R.string.text_hide
                        else R.string.text_filters
                    ),
                    style = MaterialTheme.typography.caption
                )
            }
        }
    }
}