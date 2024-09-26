package com.concordia.canary.ledger.trend_settings_edit.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme

@Composable
fun PreferredWeightSelection(
    availableSelections: () -> List<TrendSelectedUnits>,
    selectedUnits: () -> TrendSelectedUnits,
    trendSelectionUpdate: (TrendSelectedUnits) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Trend Units", style = MaterialTheme.typography.bodyLarge)

            LazyColumn {
                items(availableSelections().size) {
                    when (val item = availableSelections()[it]) {
                        is TrendSelectedUnits.ConvertTrendUnit -> {
                            var checked = false
                            if (selectedUnits().subType == item.subType) {
                                checked = true
                            }

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(ResponsiveAppTheme.dimens.medium),
                                verticalAlignment = Alignment.CenterVertically,

                                ) {
                                Text(text = item.targetUnits.displayName)
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = { trendSelectionUpdate(item) })
                            }
                        }

                        is TrendSelectedUnits.OriginalTrendUnit -> {
                            val checked = selectedUnits().subType == item.subType
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(ResponsiveAppTheme.dimens.medium),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = item.display)
                                Checkbox(
                                    checked = checked,
                                    onCheckedChange = { trendSelectionUpdate(item) })
                            }
                        }

                        TrendSelectedUnits.NoneSelected -> Unit
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun PreviewPreferredWeightSelection() {
    val lt = listOf(
        TrendSelectedUnits.ConvertTrendUnit(InputUnits.LbUnits),
        TrendSelectedUnits.ConvertTrendUnit(InputUnits.KgUnits),
        TrendSelectedUnits.OriginalTrendUnit("Keep original")
    )
    PreferredWeightSelection(
        availableSelections = { lt },
        selectedUnits = { TrendSelectedUnits.ConvertTrendUnit(InputUnits.LbUnits) },
        trendSelectionUpdate = {})

}