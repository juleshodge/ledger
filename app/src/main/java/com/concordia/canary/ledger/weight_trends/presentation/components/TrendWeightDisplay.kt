package com.concordia.canary.ledger.weight_trends.presentation.components

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight

@Composable
fun TrendWeightDisplay(trendWeight: TrendWeight) {

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {

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

                val dt = Instant.ofEpochMilli(trendWeight.observationDate)
                val dte = Date.from(dt)


                val formatter = SimpleDateFormat("dd MM yyyy HH:mm:ss")
                val final = formatter.format(dte)
                Text(text = final)
            }
            Text(
                text = "${trendWeight.originalValue} ${trendWeight.originalWeightUnits.displayName}",
                style = MaterialTheme.typography.titleLarge
            )

            if (!trendWeight.notes.isNullOrBlank()) {
                Text(text = "${trendWeight.notes}")
            }

            TrentWeighExtrasDisplay(
                currentSelections = { trendWeight.weightExtras },
                availableWeightExtras = { WeightExtras.entries.toList() })
        }

    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTrendWeightDisplay() {
    val trendWeight = TrendWeight(
        22.0,
        originalWeightUnits = InputUnits.KgUnits,
        currentWeightUnits = InputUnits.KgUnits,
        currentWeightValue = 122.0,
        notes = "Notes for a weight item",
        observationDate = 1723832586660L,
        weightExtras = emptyList()
    )
    TrendWeightDisplay(trendWeight)
}