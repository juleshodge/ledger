package com.concordia.canary.ledger.trend_settings_edit.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun DaysBack(
    modifier: Modifier = Modifier,
    daysBackVal: () -> String,
    onDaysBackChange: (String) -> Unit
) {
    OutlinedCard(modifier = modifier) {

        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Range", style = MaterialTheme.typography.bodyLarge)

            OutlinedTextField(
                value = daysBackVal(),
                singleLine = true,
                onValueChange = onDaysBackChange,
                label = {
                    Text(text = "Days")
                },
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),

                )
        }
    }
}

@Composable
@Preview
fun PreviewDaysBack() {
    DaysBack(daysBackVal = { "365" }, onDaysBackChange = {})
}