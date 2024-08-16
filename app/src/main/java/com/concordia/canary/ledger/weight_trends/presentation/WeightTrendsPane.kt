package com.concordia.canary.ledger.weight_trends.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.TrendWeightParams
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight
import com.concordia.canary.ledger.weight_trends.presentation.components.TrendWeightList
import com.concordia.canary.ledger.weight_trends.presentation.state.WeightTrendsState

@Composable
fun WeightTrendsPane(
    weightAddClicked: () -> Unit,
    trendsStateParams: TrendWeightParams
) {
    LaunchedEffect(true) {
        trendsStateParams.loadTrendWeights();
    }

    Column {
        Text(text = "Trends", style = MaterialTheme.typography.titleLarge)
        TrendWeightList(trendsStateParams.trendState().trendWeights, weightAddClicked)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeightTrendsPane() {

    val severalExtras = listOf(WeightExtras.Fed, WeightExtras.Shoes)

    val weight1 = TrendWeight(
        1.0,
        InputUnits.KgUnits,
        InputUnits.KgUnits,
        observationDate = 0L,
        weightExtras = emptyList()
    )
    val weight2 = TrendWeight(
        1.0,
        InputUnits.KgUnits,
        InputUnits.KgUnits,
        observationDate = 0L,
        weightExtras = WeightExtras.entries
    )
    val weight3 = TrendWeight(
        1.0,
        InputUnits.KgUnits,
        InputUnits.KgUnits,
        observationDate = 0L,
        weightExtras = severalExtras
    )

    val tempWeights = listOf(weight1, weight2, weight3)

    val windowSizeType = WindowSizeType(WindowSize.Expanded(500), WindowSize.Expanded(500))

    val weightTrendsState = WeightTrendsState(trendWeights = tempWeights, isLoading = false)
    val params = TrendWeightParams(
        trendState = { weightTrendsState },
        loadTrendWeights = {}
    )


    LedgerTheme(windowSizeType) {
        WeightTrendsPane(
            weightAddClicked = {},
            trendsStateParams = params
        )
    }
}