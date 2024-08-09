package com.concordia.canary.ledger.add_edit_weight.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.presentation.components.RecentWeightItem
import com.concordia.canary.ledger.add_edit_weight.presentation.components.RecentWeightItems
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType

@Composable
fun WeightCopyPane(
    modifier: Modifier = Modifier,
    recentWeights: List<Weight>, selectedWeight: Weight? = null
) {
    Card(
        modifier = modifier.padding(ResponsiveAppTheme.dimens.medium),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
    ) {
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = "Recent Weights", style = MaterialTheme.typography.bodyLarge)
            if (selectedWeight != null) {
                RecentWeightItem(weightVal = selectedWeight)
            }
            RecentWeightItems(weightVals = recentWeights)
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewWeightCopyPane() {
    val weight1 = Weight(122.0, InputUnits.KgUnits)
    val weight2 = Weight(123.0, InputUnits.KgUnits)
    val weight3 = Weight(124.0, InputUnits.KgUnits)
    val weight4 = Weight(128.0, InputUnits.KgUnits)
    val param = listOf(weight2, weight1, weight4, weight3)
    val windowSizeType = WindowSizeType(WindowSize.Compact(448), WindowSize.Compact(998))
    LedgerTheme(windowSizeType) {
        WeightCopyPane(Modifier, param)
    }
}