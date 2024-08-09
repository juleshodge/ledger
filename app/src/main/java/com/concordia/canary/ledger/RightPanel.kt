package com.concordia.canary.ledger

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun RightPanel() {
    Box(modifier = Modifier) {
        Text(text = "right1 box")
        Text(text = "right2 box")
        Text(text = "right3 box")
    }
}