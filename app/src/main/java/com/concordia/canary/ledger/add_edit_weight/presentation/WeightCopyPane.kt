package com.concordia.canary.ledger.add_edit_weight.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.presentation.components.RecentWeightItem
import com.concordia.canary.ledger.add_edit_weight.presentation.components.RecentWeightItems

@Composable
fun WeightCopyPane(recentWeights: List<Weight>, selectedWeight: Weight? = null) {
    Column() {
        Text(text = "Recent Weights", style = MaterialTheme.typography.bodyLarge)
        if (selectedWeight != null) {
            RecentWeightItem(weightVal = selectedWeight)
        }
        RecentWeightItems(weightVals = recentWeights)
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewWeightCopyPane() {
    val weight1 = Weight(122.0, InputUnits.KgUnits)
    val weight2 = Weight(122.0, InputUnits.KgUnits)
    val weight3 = Weight(122.0, InputUnits.KgUnits)
    val weight4 = Weight(122.0, InputUnits.KgUnits)
    val param = listOf(weight2, weight1, weight4, weight3)
    WeightCopyPane(param)
}