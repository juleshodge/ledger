package com.concordia.canary.ledger.weight_trends.presentation.components

import java.math.RoundingMode
import java.text.DecimalFormat
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.core.presentation.components.DateTimeDisplay
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeightEvent


@Composable
fun TrendWeightDisplay(
    trendWeight: () -> TrendWeight,
    eventSendHandler: (TrendWeightEvent) -> Unit
) {
    val df = DecimalFormat("###.##")
    df.roundingMode = RoundingMode.CEILING
    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(ResponsiveAppTheme.dimens.medium)
            .clickable { eventSendHandler.invoke(TrendWeightEvent.NavToEdit(trendWeight().id!!)) }
    ) {
        val availExtras = WeightExtras.entries.toTypedArray()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveAppTheme.dimens.medium)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopEnd,

                ) {

                DateTimeDisplay(timeStamp = trendWeight().observationDate)
            }


            if (trendWeight().currentWeightUnits != trendWeight().originalWeightUnits) {
                Text(
                    text = "~(${df.format(trendWeight().currentWeightValue)}) ${trendWeight().currentWeightUnits!!.displayName}",
                    style = MaterialTheme.typography.titleLarge
                )
            } else {
                Text(
                    text = "${df.format(trendWeight().originalValue)} ${trendWeight().originalWeightUnits.displayName}",
                    style = MaterialTheme.typography.titleLarge
                )
            }


            if (!trendWeight().notes.isNullOrBlank()) {
                Text(text = "${trendWeight().notes}")
                Spacer(modifier = Modifier.height(ResponsiveAppTheme.dimens.medium))
            }

            val stableWeightSels = trendWeight().weightExtras.toTypedArray()

            TrentWeighExtrasDisplay(
                currentSelections = { stableWeightSels },
                availableWeightExtras = { availExtras })
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTrendWeightDisplay() {
    val trendWeight = TrendWeight(
        5L,
        22.0,
        originalWeightUnits = InputUnits.KgUnits,
        currentWeightUnits = InputUnits.KgUnits,
        currentWeightValue = 122.0,
        notes = "Notes for a weight item",
        observationDate = 1723832586660L,
        weightExtras = emptyList()
    )
    TrendWeightDisplay({ trendWeight }, eventSendHandler = {})
}