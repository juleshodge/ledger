package com.concordia.canary.ledger.add_edit_weight.presentation

import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.NoteEditParams
import com.concordia.canary.ledger.R
import com.concordia.canary.ledger.WeightEditParams
import com.concordia.canary.ledger.WeightParams
import com.concordia.canary.ledger.add_edit_weight.presentation.components.ExtrasSelectionEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.NotesEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.ReadOnlyTimeDisplay
import com.concordia.canary.ledger.add_edit_weight.presentation.components.WeighValueEntry
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.util.UiText

@Composable
fun WeightEditPane(
    modifier: Modifier = Modifier,
    weightEditParams: WeightEditParams,
    ort: Orientation,
    weightId: () -> Long,
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    LaunchedEffect(key1 = weightId) {
        weightEditParams.onLoadWeightEntry(weightId())
    }

    val availExtras by remember {
        mutableStateOf(WeightExtras.entries.toList())
    }

    val mediumLarge = ResponsiveAppTheme.dimens.mediumLarge

    if (openAlertDialog.value) {
        AlertDialogExample(onDismissRequest = {
            openAlertDialog.value = false
        }, onConfirmation = {
            openAlertDialog.value = false
            weightEditParams.onDelete(weightId())
        }, dialogText = "Confirm Deletion", dialogTitle = "Delete current weight entry?")
    }

    Column(
        modifier = modifier
            .padding(mediumLarge)
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.Start,

        verticalArrangement = Arrangement.spacedBy(mediumLarge)
    ) {
        if (ort == Orientation.WIDER) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center

            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(mediumLarge),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(R.string.update_weight_str),
                        style = MaterialTheme.typography.displayLarge
                    )

                    HorizontalDivider()
                }
            }

        } else {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.update_weight_str),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {

            WeighValueEntry(
                modifier = Modifier.fillMaxWidth(.8f),
                viewModelParams = weightEditParams.weightParams
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    enabled = true,
                    onClick = {
                        openAlertDialog.value = true
                    },

                    ) {
                    Icon(
                        Icons.Filled.DeleteForever,
                        "Delete",
                    )
                }
                Button(
                    onClick = {
                        weightEditParams.onUpdate()
                    },
                    enabled = weightEditParams.weightParams.weightValueValid(),

                    ) {
                    Icon(
                        Icons.Filled.Upload,
                        "Update",
                    )
                }
            }
        }

        ReadOnlyTimeDisplay(entryVal = { weightEditParams.weightObsTimeValue() })

        NotesEntry(modifier = Modifier.fillMaxWidth(), weightEditParams.noteEditParams)
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            ExtrasSelectionEntry(

                ort = ort,
                currentSelections = {
                    weightEditParams.selectedExtras()
                },
                updateExtraSelection = weightEditParams.updateExtraSelection,

                availableWeightExtras = {
                    availExtras
                })
        }
    }
}


@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
) {

    AlertDialog(

        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Dismiss")
            }
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewWeightEditPane() {
    val weightParams = WeightParams(
        weightValue = { "23" },
        weightValueUpdate = {},
        weightValueError = { UiText.DynamicText("") },
        weightUnits = { InputUnits.KgUnits },
        onUnitsChanged = {},
        weightValueValid = { true },
        availableWeightUnits = { InputUnits.entries.toList() })
    val weightEditParams =
        WeightEditParams(onUpdate = {}, weightParams = weightParams,
            weightObsTimeValue = { 1L },
            noteEditParams = NoteEditParams(
                weightNotesValue = { "" }, weightNotesValueUpdate = {}
            ),
            onLoadWeightEntry = {},
            onDelete = {},

            selectedExtras = { WeightExtras.entries.toList() },
            updateExtraSelection = fun(
                weightExtra: WeightExtras,
                selectionVal: Boolean
            ) {
                weightExtra.displayName
            })
    val ort = Orientation.WIDER
    WeightEditPane(weightEditParams = weightEditParams, weightId = { 7L }, ort = ort)
}