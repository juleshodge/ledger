package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType

@Composable
fun RecentWeightItem(weightVal: Weight) {
    val curSize = ResponsiveAppTheme.windowSize.width
    if (curSize is WindowSize.Expanded) {
        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.padding(ResponsiveAppTheme.dimens.small)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ResponsiveAppTheme.dimens.small),
                horizontalArrangement = Arrangement.End,

                ) {
                Text(text = "${weightVal.weightValue}")
                Spacer(modifier = Modifier.width(ResponsiveAppTheme.dimens.medium))
                Text(text = weightVal.units.displayName)
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                items(items = weightVal.extras, itemContent = { item ->
                    Column(modifier = Modifier.padding(ResponsiveAppTheme.dimens.medium)) {
                        Text(text = item.displayName)
                    }
                })
            }
        }

    } else {
        Card(
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            modifier = Modifier.padding(ResponsiveAppTheme.dimens.small)

        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ResponsiveAppTheme.dimens.small),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(text = "${weightVal.weightValue}")
                Spacer(modifier = Modifier.width(ResponsiveAppTheme.dimens.medium))
                Text(text = weightVal.units.displayName)

            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PreviewRecentWeightItem() {

    val selections = WeightExtras.entries

    val testWeight = Weight(155.0, InputUnits.KgUnits, System.currentTimeMillis(), selections)
    val windowSizeType = WindowSizeType(WindowSize.Compact(400), WindowSize.Compact(600))
    LedgerTheme(windowSizeType) {
        RecentWeightItem(testWeight)

    }
}
