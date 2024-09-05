package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HelperLabelText(str: String) {
    Text(
        text = str, style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
}

@Preview
@Composable
fun PreviewHelperLabelTet() {
    HelperLabelText(str = "some text")
}