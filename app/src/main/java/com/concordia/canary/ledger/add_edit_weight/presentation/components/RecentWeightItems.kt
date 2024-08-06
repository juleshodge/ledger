package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight

@Composable
fun RecentWeightItems(weightVals: List<Weight>) {

    Box(
        modifier = Modifier
            .border(3.dp, MaterialTheme.colorScheme.secondary)
            .padding(10.dp)
    ) {
        LazyColumn {
            items(weightVals.size) { wtIndex ->
                val weighVal = weightVals[wtIndex]
                RecentWeightItem(weightVal = weighVal)
            }
        }
    }

}

@Composable
@Preview(
    showBackground = true
)
fun PreviewRecentWeightItems() {
    val weight1 = Weight(122.0, InputUnits.KgUnits)
    val weight2 = Weight(122.0, InputUnits.KgUnits)
    val weight3 = Weight(122.0, InputUnits.KgUnits)
    val weight4 = Weight(122.0, InputUnits.KgUnits)
    val param = listOf(weight2, weight1, weight4, weight3)
    RecentWeightItems(param)
}