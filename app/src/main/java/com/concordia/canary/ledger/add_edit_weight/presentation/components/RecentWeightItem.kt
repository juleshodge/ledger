package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType

@Composable
fun RecentWeightItem(weightVal: Weight) {
    val curSize = ResponsiveAppTheme.windowSize.width
    if (curSize is WindowSize.Expanded) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "${weightVal.weightValue}")
            Spacer(modifier = Modifier.width(ResponsiveAppTheme.dimens.medium))
            Text(text = weightVal.units.displayName)
        }
    } else {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(text = "${weightVal.weightValue}")
            Spacer(modifier = Modifier.width(ResponsiveAppTheme.dimens.medium))
            Text(text = weightVal.units.displayName)

        }
    }


}

@Composable
@Preview(showBackground = true)
fun PreviewRecentWeightItem() {
    val testWeight = Weight(155.0, InputUnits.KgUnits)
    val windowSizeType = WindowSizeType(WindowSize.Compact(800), WindowSize.Compact(1200))
    LedgerTheme(windowSizeType) {
        RecentWeightItem(testWeight)

    }
}
