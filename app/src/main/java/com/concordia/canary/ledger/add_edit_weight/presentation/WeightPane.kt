package com.concordia.canary.ledger.add_edit_weight.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.NoteEditParams
import com.concordia.canary.ledger.RecentWeightParams
import com.concordia.canary.ledger.WeightAddParams
import com.concordia.canary.ledger.WeightParams
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.presentation.state.RecentWeightsState
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.util.UiText

@Composable
fun WeightPane(
    modifier: Modifier = Modifier,
    viewModelParams: WeightAddParams,
    recentItems: RecentWeightParams,
    ort: Orientation,
    weightAddNavBack: () -> Unit
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

            WeightAddPane(
                Modifier.fillMaxWidth(.6f),
                viewModelParams = viewModelParams,
                ort = ort,
            )
            WeightCopyPane(modifier = Modifier.fillMaxWidth(), recentItems)
        }
    } else {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            IconButton(onClick = { weightAddNavBack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Goback",

                    )
            }

            WeightAddPane(
                Modifier.fillMaxWidth(),
                viewModelParams = viewModelParams,
                ort = ort,
            )
            WeightCopyPane(modifier = Modifier.fillMaxWidth(), recentItems)
        }
    }
}


@Preview(
    showBackground = true,
    device = "spec:width=448dp,height=998dp,dpi=480,isRound=false,chinSize=0dp,orientation=landscape"
)
//@Preview(showBackground = true)
@Composable
fun PreviewWeightPane(modifier: Modifier = Modifier) {

    val weightAddParams = WeightAddParams(
        onSavePressed = { TODO() },
        selectedExtras = { WeightExtras.entries },
        weightParams = WeightParams(
            weightValue = { "122.2" },
            weightValueValid = { true },
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

        noteEditParams = NoteEditParams(
            weightNotesValue = { "some notes value" },
            weightNotesValueUpdate = {}),
        weightObsTimeValue = { 1724001833775L },
        weightObsTimeValueUpdate = {},

        )

    val selections = WeightExtras.entries.filter { it -> it.name == "Boots" || it.name == "Fed" }


    val weight1 = Weight(
        null,
        122.0,
        InputUnits.KgUnits,
        System.currentTimeMillis(),
        selections,
        notes = null,
        active = true
    )
    val weight2 = Weight(
        null,
        123.0,
        InputUnits.KgUnits,
        System.currentTimeMillis(),
        selections,
        notes = null,
        active = true
    )
    val weight3 = Weight(
        null,
        124.0,
        InputUnits.KgUnits,
        System.currentTimeMillis(),
        selections,
        notes = null,
        active = true
    )
    val weight4 = Weight(
        null,
        128.0,
        InputUnits.KgUnits,
        System.currentTimeMillis(),
        selections,
        notes = null,
        active = true
    )
    val param = listOf(weight2, weight1, weight4, weight3)

    val recentWeightsState = RecentWeightsState(
        param, selectedRecentWeight = null,
        hasLoaded = true,
        isLoading = false,
        isError = false
    )

    val recentWeightParams =
        RecentWeightParams(loadRecentWeights = { TODO() }, recentState = { recentWeightsState })

    WeightPane(
        modifier = modifier,
        viewModelParams = weightAddParams,
        ort = Orientation.WIDER,
        recentItems = recentWeightParams,
        weightAddNavBack = {}
    )
}