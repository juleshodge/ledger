package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight

@Composable
fun RecentWeightItem(weightVal: Weight) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "${weightVal.weightValue}")
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = weightVal.units.displayName)
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewRecentWeightItem() {
    val testWeight = Weight(155.0, InputUnits.KgUnits)
    RecentWeightItem(testWeight)
}