package com.concordia.canary.ledger

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun LeftPane() {
    Box(modifier = Modifier) {
        Row() {
            Text(text = "left1 box")
            Text(text = "left2 box")
            Text(text = "left3 box")
        }

    }
}