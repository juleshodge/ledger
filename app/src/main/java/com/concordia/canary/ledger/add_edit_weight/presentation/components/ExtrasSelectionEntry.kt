package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.R
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType
import com.concordia.canary.ledger.util.UiText

@Composable
fun ExtrasSelectionEntry(
    availableWeightExtras: () -> List<WeightExtras>,
    currentSelections: () -> List<WeightExtras>,
    updateExtraSelection: (WeightExtras, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    ort: Orientation

) {

    OutlinedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = modifier
                .padding(ResponsiveAppTheme.dimens.medium)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = UiText.StringResource(R.string.weight_extras_label).asString(),
                style = MaterialTheme.typography.headlineMedium
            )
            ExtrasSelectionUiContainer(
                availableWeightExtras = { availableWeightExtras() },
                currentSelections = currentSelections,
                updateExtraSelection = updateExtraSelection,
                ort = ort
            )
        }
    }
}

@Composable
private fun ExtrasSelectionUiContainer(
    availableWeightExtras: () -> List<WeightExtras>,
    currentSelections: () -> List<WeightExtras>,
    updateExtraSelection: (WeightExtras, Boolean) -> Unit,
    modifier: Modifier = Modifier,
    ort: Orientation

) {
    if (ort == Orientation.TALLER) {

        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                intArrayOf(0, 1, 2).forEach { availExtraIndex ->
                    val availExtra = availableWeightExtras()[availExtraIndex]
                    Column(modifier = Modifier.padding(ResponsiveAppTheme.dimens.small)) {

                        Text(text = availExtra.displayName)
                        Checkbox(
                            checked =
                            currentSelections()
                                .contains(availExtra),
                            onCheckedChange = { newVal ->
                                updateExtraSelection(availExtra, newVal)
                            }
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                intArrayOf(3, 4).forEach { availExtraIndex ->
                    val availExtra = availableWeightExtras()[availExtraIndex]
                    Column(modifier = Modifier.padding(ResponsiveAppTheme.dimens.small)) {

                        Text(text = availExtra.displayName)
                        Checkbox(
                            checked =
                            currentSelections()
                                .contains(availExtra),
                            onCheckedChange = { newVal ->
                                updateExtraSelection(availExtra, newVal)
                            }
                        )
                    }
                }
            }
        }

    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            availableWeightExtras().forEach { availExtra ->

                Column(modifier = Modifier.padding(ResponsiveAppTheme.dimens.small)) {

                    Text(text = availExtra.displayName)
                    Checkbox(
                        checked =
                        currentSelections()
                            .contains(availExtra),
                        onCheckedChange = { newVal ->
                            updateExtraSelection(availExtra, newVal)
                        }
                    )
                }
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    device = "spec:width=600dp,height=1100dp,dpi=480,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PreviewExtrasSelectionEntry() {
    val windowSizeType = WindowSizeType(WindowSize.Expanded(600), WindowSize.Expanded(1100))
    LedgerTheme(windowSizeType) {
        ExtrasSelectionEntry(
            ort = Orientation.WIDER,
            availableWeightExtras = { WeightExtras.entries.toList() },
            currentSelections = { listOf(WeightExtras.entries[0]) },
            updateExtraSelection = fun(
                weightExtra: WeightExtras,
                selectionVal: Boolean
            ) {
                weightExtra.displayName
            })
    }
}