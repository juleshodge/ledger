package com.concordia.canary.ledger.add_edit_weight.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.presentation.components.ExtrasSelectionEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.NotesEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.PreviewExtrasSelectionEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.PreviewNotesEntryContainer
import com.concordia.canary.ledger.add_edit_weight.presentation.components.PreviewWeightEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.WeighValueEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.WeightEntryViewModel

@Composable
fun WeighAddPane(
    modifier: Modifier = Modifier,
) {
    val availExtras by remember {
        mutableStateOf(WeightExtras.entries.toList())
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Enter Weight",
            style = MaterialTheme.typography.headlineLarge
        )

        val viewModel: WeightEntryViewModel = hiltViewModel()



        WeighValueEntry()
        NotesEntry()
        ExtrasSelectionEntry(
            currentSelections = {
                viewModel.entryState.selectedExtras
            },
            updateExtraSelection = viewModel::onWeightExtraSelected,
            availableWeightExtras = {
                availExtras
            })

        Row(modifier = Modifier.align(Alignment.End)) {
            OutlinedIconButton(
                onClick = { viewModel.onSavePressed() },
                enabled = viewModel.entryState.weightValueValid,
                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.Green)
            ) {
                Icon(

                    imageVector = Icons.Default.Save,
                    modifier = Modifier
                        .size(64.dp),
                    contentDescription = "Save"
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWeightAddPane() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)

    ) {
        Text(text = "Enter Weight", style = MaterialTheme.typography.headlineLarge)

        PreviewWeightEntry()
        PreviewNotesEntryContainer()
        PreviewExtrasSelectionEntry()


        Row(
            modifier = Modifier
                .align(Alignment.End)

        ) {
            OutlinedIconButton(
                onClick = { },

                enabled = true,
                colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.secondary)

            ) {
                Icon(
                    imageVector = Icons.Default.Save,
                    modifier = Modifier.size(96.dp).padding(5.dp),
                    contentDescription = "Save"
                )
            }
        }
    }
}