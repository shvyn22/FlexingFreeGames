package shvyn22.flexingfreegames.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.presentation.ui.theme.dimens

@Composable
fun DropdownList(
    selectedIndex: Int,
    onSelectedIndexChange: (Int) -> Unit,
    items: List<String>,
    modifier: Modifier = Modifier
) {
    val isExpanded = remember { mutableStateOf(false) }

    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.small
                )
                .clickable {
                    isExpanded.value = true
                }
                .padding(MaterialTheme.dimens.padding.paddingContentSmall)
        ) {
            Text(
                text = items[selectedIndex],
                style = MaterialTheme.typography.caption
            )

            Image(
                painter = painterResource(id = R.drawable.ic_arrow_down),
                contentDescription = null,
            )
        }

        DropdownMenu(
            expanded = isExpanded.value,
            onDismissRequest = { isExpanded.value = false },
            modifier = Modifier
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(
                    onClick = {
                        onSelectedIndexChange(index)
                        isExpanded.value = false
                    }
                ) {
                    Text(
                        text = item
                    )
                }
            }
        }
    }
}