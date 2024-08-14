package com.concordia.canary.ledger.add_edit_weight.presentation


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.RecentWeightParams
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.presentation.components.RecentWeightItems
import com.concordia.canary.ledger.add_edit_weight.presentation.state.RecentWeightsState
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType

@Composable
fun WeightCopyPane(
    modifier: Modifier = Modifier,
    recentItemsParams: RecentWeightParams
) {
    LaunchedEffect(true) {
        recentItemsParams.loadRecentWeights();
    }
    Card(
        modifier = modifier
            .padding(ResponsiveAppTheme.dimens.mediumLarge),

        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveAppTheme.dimens.verySmall
        )
    ) {
        if (recentItemsParams.recentState().isLoading) {
            Text(text = "Loading...")
        } else {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Recent Entries",
                    modifier = Modifier.padding(top = ResponsiveAppTheme.dimens.small),
                    style = MaterialTheme.typography.bodyLarge
                )

                RecentWeightItems(weightVals = recentItemsParams.recentState().recentWeights)
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewWeightCopyPane() {
    val windowSizeType = WindowSizeType(WindowSize.Compact(448), WindowSize.Compact(998))

    val selections = WeightExtras.entries.filter { it -> it.name == "Boots" || it.name == "Fed" }

    val weight1 = Weight(122.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val weight2 = Weight(123.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val weight3 = Weight(124.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val weight4 = Weight(128.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val param = listOf(weight2, weight1, weight4, weight3)

    val recentWeightsState = RecentWeightsState(
        param, selectedRecentWeight = null,
        hasLoaded = true,
        isLoading = false,
        isError = false
    )

    val recentWeightParams =
        RecentWeightParams(loadRecentWeights = { TODO() }, recentState = { recentWeightsState })

    LedgerTheme(windowSizeType) {
        WeightCopyPane(Modifier, recentWeightParams)
    }
}