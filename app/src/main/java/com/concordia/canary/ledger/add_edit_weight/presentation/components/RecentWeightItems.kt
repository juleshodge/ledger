package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme

@Composable
fun RecentWeightItems(weightVals: List<Weight>) {

    Box(
        modifier = Modifier
            .padding(ResponsiveAppTheme.dimens.medium)
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
    val selections = WeightExtras.entries.filter { it -> it.name == "Boots" || it.name == "Fed" }


    val weight1 = Weight(120.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val weight2 = Weight(122.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val weight3 = Weight(124.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val weight4 = Weight(128.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val param = listOf(weight2, weight1, weight4, weight3)
    RecentWeightItems(param)
}