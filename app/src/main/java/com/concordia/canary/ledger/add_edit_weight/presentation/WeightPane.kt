package com.concordia.canary.ledger.add_edit_weight.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.WeightEditParams
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.util.UiText

@Composable
fun WeightPane(
    modifier: Modifier = Modifier,
    viewModelParams: WeightEditParams,
    ort: Orientation
) {
    if (ort == Orientation.WIDER) {
        Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
            WeightAddPane(
                Modifier.fillMaxWidth(.7f),
                viewModelParams = viewModelParams,
                ort = ort,
            )
            WeightCopyPane(recentWeights = emptyList(), modifier = Modifier.fillMaxWidth())
        }
    } else {
        Column(modifier = modifier.fillMaxSize()) {
            WeightAddPane(
                Modifier.fillMaxWidth(),
                viewModelParams = viewModelParams,
                ort = ort,
            )
            WeightCopyPane(recentWeights = emptyList(), modifier = Modifier.fillMaxWidth())
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

    val weightEditParams = WeightEditParams(onSavePressed = { TODO() },
        selectedExtras = { WeightExtras.entries },
        weightValue = { "122.2" },
        updateExtraSelection = fun(
            weightExtra: WeightExtras,
            selectionVal: Boolean
        ) {
            weightExtra.displayName
        },
        weightValueValid = { true },
        weightUnits = { InputUnits.KgUnits },
        availableWeightUnits = {
            InputUnits.entries.toList()
        },
        weightValueUpdate = fun(s: String) {},
        weightValueError = { UiText.DynamicText("A error") },
        onUnitsChanged = fun(a: InputUnits) {},
        weightNotesValue = { "a set of notes" }, weightNotesValueUpdate = fun(a: String) {}
    )


    WeightPane(
        modifier = modifier,
        viewModelParams = weightEditParams,
        ort = Orientation.WIDER
    )

}