package com.concordia.canary.ledger.add_edit_weight.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.NoteEditParams
import com.concordia.canary.ledger.R
import com.concordia.canary.ledger.WeightAddParams
import com.concordia.canary.ledger.WeightParams
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.presentation.components.ExtrasSelectionEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.HelperLabelText
import com.concordia.canary.ledger.add_edit_weight.presentation.components.NotesEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.TimeSpecificationEntry
import com.concordia.canary.ledger.add_edit_weight.presentation.components.WeighValueEntry
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.util.UiText


@Composable
fun WeightAddPane(
    modifier: Modifier = Modifier,
    viewModelParams: WeightAddParams,
    ort: Orientation
) {
    val availExtras by remember {
        mutableStateOf(WeightExtras.entries.toList())
    }

    val mediumLarge = ResponsiveAppTheme.dimens.mediumLarge

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
                        text = stringResource(R.string.enter_weight_headline_str),
                        style = MaterialTheme.typography.displayLarge
                    )

                    HorizontalDivider()
                }
            }

        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(R.string.enter_weight_headline_str),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top

        ) {

            WeighValueEntry(
                modifier = Modifier.fillMaxWidth(.8f),
                viewModelParams.weightParams
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Button(
                    onClick = {
                        viewModelParams.onSavePressed()
                    },
                    enabled = viewModelParams.weightParams.weightValueValid(),
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Icon(
                        Icons.Filled.Save,
                        "Save",
                    )
                }
                HelperLabelText(str = stringResource(R.string.save_label_str))
            }

        }
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            HelperLabelText("OPTIONAL")
        }

        TimeSpecificationEntry(
            entryVal = { viewModelParams.weightObsTimeValue() },
            entryValUpdate = { viewModelParams.weightObsTimeValueUpdate }
        )

        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        NotesEntry(modifier = Modifier.fillMaxWidth(), viewModelParams.noteEditParams)
        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
        ) {
            ExtrasSelectionEntry(

                ort = ort,
                currentSelections = {
                    viewModelParams.selectedExtras()
                },
                updateExtraSelection = viewModelParams.updateExtraSelection,
                availableWeightExtras = {
                    availExtras
                })
        }
    }
}


@Preview(
    showBackground = true,
    device = "spec:width=448dp,height=998dp,dpi=480,isRound=false,chinSize=0dp,orientation=portrait"
)
//@Preview(showBackground = true)
@Composable
fun PreviewWeightAddPane(modifier: Modifier = Modifier) {
    val weightAddParams = WeightAddParams(
        onSavePressed = { TODO() },
        selectedExtras = { WeightExtras.entries },
        weightParams = WeightParams(
            weightValue = { "122.2" },
            weightValueValid = { false },
            weightUnits = { InputUnits.KgUnits },
            availableWeightUnits = {
                InputUnits.entries.toList()
            },
            weightValueUpdate = fun(s: String) {},
            weightValueError = { UiText.DynamicText("A error") },
            onUnitsChanged = fun(a: InputUnits) {},
        ),

        updateExtraSelection = fun(
            weightExtra: WeightExtras,
            selectionVal: Boolean
        ) {
            weightExtra.displayName
        },

        weightObsTimeValue = { 1724001833775L },
        weightObsTimeValueUpdate = {},
        noteEditParams = NoteEditParams(weightNotesValue = { "" }, weightNotesValueUpdate = {}),
    )

    WeightAddPane(
        viewModelParams = weightAddParams,
        ort = Orientation.WIDER,
    )
}