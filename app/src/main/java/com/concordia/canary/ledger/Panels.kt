package com.concordia.canary.ledger

import android.provider.Settings.Panel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme

@Composable
fun Panels(modifier: Modifier) {
    val ortMode = ResponsiveAppTheme.ortMode

    if (ortMode == Orientation.WIDER) {
        Row() {
            LeftPane()
            RightPanel()
        }
    } else {
        Column(modifier = modifier) {
            LeftPane()
            RightPanel()
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewPanel() {
    Panels(modifier = Modifier)
}