package com.concordia.canary.ledger.weight_trends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme


@Composable
fun TrentWeighExtrasDisplay(
    currentSelections: () -> Array<WeightExtras>,
    availableWeightExtras: () -> Array<WeightExtras>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        intArrayOf(0, 1, 2).forEach { availExtraIndex ->
            val availExtra = availableWeightExtras()[availExtraIndex]
            Column(modifier = Modifier.padding(ResponsiveAppTheme.dimens.small)) {

                Text(text = availExtra.displayName)
                Checkbox(
                    enabled = false,
                    checked = currentSelections()
                        .contains(availExtra),
                    onCheckedChange = { newVal ->
                        {}
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
                    enabled = false,
                    checked = currentSelections()
                        .contains(availExtra),
                    onCheckedChange = { newVal ->
                        {}
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTrendWeightExtraDisplay() {
    val currentSelections = { listOf(WeightExtras.Boots) }
    TrentWeighExtrasDisplay(
        currentSelections = { currentSelections().toTypedArray() },
        availableWeightExtras = { WeightExtras.entries.toTypedArray() })
}