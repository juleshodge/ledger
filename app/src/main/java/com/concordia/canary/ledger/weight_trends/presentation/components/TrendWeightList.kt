package com.concordia.canary.ledger.weight_trends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Add
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.R
import com.concordia.canary.ledger.TrendWeightParams
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeightEvent
import com.concordia.canary.ledger.weight_trends.presentation.state.WeightTrendsState

@Composable
fun TrendWeightList(
    trendsStateParams: TrendWeightParams
) {
    val trendWeights = trendsStateParams.trendState().trendWeights;

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ResponsiveAppTheme.dimens.medium)
    ) {

        Row(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {

            TrendStatsDisplay(stats = trendsStateParams.trendState().trendWeightStats)

            Column {
                OutlinedIconButton(onClick = { trendsStateParams.eventSendHandler(TrendWeightEvent.NavToTrendSettings) }) {
                    Icon(

                        imageVector = Icons.TwoTone.Settings,
                        contentDescription = "Settings"
                    )
                }

                OutlinedIconButton(
                    onClick = {
                        trendsStateParams.eventSendHandler(TrendWeightEvent.NavToAdd)
                    },

                    ) {
                    Icon(

                        imageVector = Icons.TwoTone.Add,
                        contentDescription = "Add Weight"
                    )
                }
            }
        }

        if (trendWeights.isEmpty()) {
            Spacer(modifier = Modifier.height(ResponsiveAppTheme.dimens.medium))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = stringResource(R.string.no_entries_to_display_str))
            }
        }

        val listSize = trendWeights.size

        val keys = trendWeights.map { tw -> tw.id!! }.toTypedArray()

        val stableList = trendWeights.toTypedArray()

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(listSize,
                key = { keys[it] }) {
                TrendWeightDisplay(
                    { stableList[it] },
                    eventSendHandler = trendsStateParams.eventSendHandler
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewWeightList() {
    val severalExtras = listOf(WeightExtras.Fed, WeightExtras.Shoes)

    val weight1 = TrendWeight(
        1L,
        1.0,
        InputUnits.KgUnits,
        InputUnits.KgUnits,
        observationDate = 0L,
        weightExtras = emptyList()
    )
    val weight2 = TrendWeight(
        2L,
        1.0,
        InputUnits.KgUnits,
        InputUnits.KgUnits,
        observationDate = 0L,
        weightExtras = WeightExtras.entries
    )
    val weight3 = TrendWeight(
        3L,
        1.0,
        InputUnits.KgUnits,
        InputUnits.KgUnits,
        observationDate = 0L,
        weightExtras = severalExtras
    )
    val tempWeights = listOf(weight1, weight2, weight3)

    val windowSizeType = WindowSizeType(WindowSize.Expanded(500), WindowSize.Expanded(500))

    val state = {
        WeightTrendsState(
            trendWeights = tempWeights,
            trendWeightStats = null,
            false
        )
    }

    val params = TrendWeightParams(
        loadTrendWeights = {},
        eventSendHandler = {},
        trendState = state
    )
    LedgerTheme(windowSizeType) {
        TrendWeightList(params)
    }
}