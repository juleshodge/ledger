package com.concordia.canary.ledger.trend_settings_edit.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.trend_settings_edit.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.trend_settings_edit.presentation.components.DaysBack
import com.concordia.canary.ledger.trend_settings_edit.presentation.components.PreferredWeightSelection
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme

@Composable
fun TrendSettingsPane(
    getSelectableUnits: () -> List<TrendSelectedUnits>,
    trendSelectionUpdate: (TrendSelectedUnits) -> Unit,
    selectedUnits: () -> TrendSelectedUnits,
    daysBackVal: () -> String,
    onDaysBackChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onSettingsNavBack: () -> Unit,
    ort: Orientation
) {
    val mediumLarge = ResponsiveAppTheme.dimens.mediumLarge


    if (ort == Orientation.WIDER) {
        Column(
            modifier = modifier
                .padding(mediumLarge),
            verticalArrangement = Arrangement.spacedBy(mediumLarge)
        ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onSettingsNavBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Goback",

                        )
                }
                Text(text = "Trend Settings", style = MaterialTheme.typography.displayLarge)

            }
            HorizontalDivider()
            Row() {
                PreferredWeightSelection(
                    availableSelections = getSelectableUnits,
                    trendSelectionUpdate = trendSelectionUpdate,
                    selectedUnits = selectedUnits,
                    modifier = Modifier
                        .padding(ResponsiveAppTheme.dimens.medium)
                        .fillMaxWidth(.5f)
                )
                Column {
                    DaysBack(
                        daysBackVal = daysBackVal,
                        onDaysBackChange = onDaysBackChange,
                        modifier = Modifier
                            .padding(ResponsiveAppTheme.dimens.medium)

                    )
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(ResponsiveAppTheme.dimens.medium)
                    ) {
                        Button(onClick = { /*TODO*/ }) {
                            Text(text = "Save")
                        }
                    }
                }

            }
        }

    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(mediumLarge),

            ) {
            Row(
                modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { onSettingsNavBack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBackIosNew,
                        contentDescription = "Goback",

                        )
                }

            }
            Text(text = "Trend Settings", style = MaterialTheme.typography.displayLarge)
            HorizontalDivider()
            PreferredWeightSelection(
                availableSelections = getSelectableUnits,
                trendSelectionUpdate = trendSelectionUpdate,
                selectedUnits = selectedUnits,
                modifier = Modifier.padding(ResponsiveAppTheme.dimens.medium)
            )

            DaysBack(
                daysBackVal = daysBackVal,
                onDaysBackChange = onDaysBackChange,
                modifier = Modifier.padding(ResponsiveAppTheme.dimens.medium)
            )

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Save")
            }
        }
    }
}

@Composable
@Preview(
    showBackground = true,
    device = "spec:width=448dp,height=998dp,dpi=480,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PreviewTrendSettingsPane() {
    val lt = listOf(
        TrendSelectedUnits.ConvertTrendUnit(InputUnits.LbUnits),
        TrendSelectedUnits.ConvertTrendUnit(InputUnits.KgUnits),
        TrendSelectedUnits.OriginalTrendUnit("Keep original")
    )
    TrendSettingsPane(
        getSelectableUnits = { lt },
        selectedUnits = { TrendSelectedUnits.ConvertTrendUnit(InputUnits.LbUnits) },
        trendSelectionUpdate = {},
        daysBackVal = { "365" },
        onDaysBackChange = {},
        onSettingsNavBack = {},
        ort = Orientation.WIDER
    )
}