package com.concordia.canary.ledger.add_edit_weight.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.NoteEditParams
import com.concordia.canary.ledger.WeightEditParams
import com.concordia.canary.ledger.WeightParams
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.util.UiText

@Composable
fun WeightEdit(
    weightEditParams: WeightEditParams,
    modifier: Modifier = Modifier,
    ort: Orientation,
    weightAddNavBack: () -> Unit,
    weightId: () -> Long,
) {
    if (ort == Orientation.WIDER) {
        Row(
            modifier = modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { weightAddNavBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Goback",

                    )
            }
            WeightEditPane(
                Modifier.fillMaxWidth(.6f),
                weightEditParams = weightEditParams,
                ort = ort,
                weightId = weightId
            )

        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            IconButton(onClick = { weightAddNavBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Goback",

                    )
            }
            WeightEditPane(
                Modifier.fillMaxWidth(),
                weightEditParams = weightEditParams,
                ort = ort,
                weightId = weightId
            )
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewWeightEdit() {
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
    val ort = Orientation.TALLER
    WeightEdit(
        weightEditParams = weightEditParams,
        weightId = { 7L },
        ort = ort,
        weightAddNavBack = {})
}