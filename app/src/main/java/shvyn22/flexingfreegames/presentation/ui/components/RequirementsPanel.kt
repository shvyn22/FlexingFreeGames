package shvyn22.flexingfreegames.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import shvyn22.flexingfreegames.R
import shvyn22.flexingfreegames.data.local.model.SystemRequirementsModel
import shvyn22.flexingfreegames.presentation.ui.theme.dimens

@Composable
fun RequirementsPanel(
    requirements: SystemRequirementsModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.dimens.padding.paddingContentSmall)
        ) {
            Text(
                text = stringResource(id = R.string.text_os, requirements.os),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = stringResource(id = R.string.text_processor, requirements.processor),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = stringResource(id = R.string.text_graphics, requirements.graphics),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = stringResource(id = R.string.text_memory, requirements.memory),
                style = MaterialTheme.typography.body2,
            )
            Text(
                text = stringResource(id = R.string.text_storage, requirements.storage),
                style = MaterialTheme.typography.body2,
            )
        }
    }
}