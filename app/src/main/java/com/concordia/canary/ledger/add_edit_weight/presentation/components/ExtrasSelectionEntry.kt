package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras

@Composable
fun ExtrasSelectionEntry(
    availableWeightExtras: () -> List<WeightExtras>,
    currentSelections: () -> List<WeightExtras>,
    updateExtraSelection: (WeightExtras, Boolean) -> Unit

) {
    OutlinedCard(modifier = Modifier.padding(10.dp)) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = "Extras", style = MaterialTheme.typography.bodyLarge)
            ExtrasSelectionUiContainer(
                availableWeightExtras = { availableWeightExtras() },
                currentSelections = currentSelections,
                updateExtraSelection = updateExtraSelection

            )
        }
    }

}

@Composable
private fun ExtrasSelectionUiContainer(
    availableWeightExtras: () -> List<WeightExtras>,
    currentSelections: () -> List<WeightExtras>,
    updateExtraSelection: (WeightExtras, Boolean) -> Unit

) {

    Row {
        availableWeightExtras().forEach { availExtra ->

            Column(modifier = Modifier.padding(5.dp)) {

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

@Composable
@Preview(showBackground = true)
fun PreviewExtrasSelectionEntry() {
    ExtrasSelectionEntry(
        availableWeightExtras = { WeightExtras.entries.toList() },
        currentSelections = { listOf(WeightExtras.entries[0]) },
        updateExtraSelection = fun(
            weightExtra: WeightExtras,
            selectionVal: Boolean
        ) {
            weightExtra.displayName
        })
}
