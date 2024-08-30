package com.concordia.canary.ledger.add_edit_weight.presentation.components

import java.util.Calendar
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccessTime
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ReadOnlyTimeDisplay(
    modifier: Modifier = Modifier,
    entryVal: () -> Long
) {
    val cal = setCalendarInstance(Calendar.getInstance(), entryVal())

    val mHour = cal[Calendar.HOUR_OF_DAY]
    val mMinute = cal[Calendar.MINUTE]

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        if (mMinute < 10) {
            Text(
                text = "${mHour}:0${mMinute}",
            )
        } else {
            Text(
                text = "${mHour}:${mMinute}",
            )
        }
        IconButton(
            onClick = {}
        ) {
            Icon(imageVector = Icons.TwoTone.AccessTime, contentDescription = "Time")
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewReadOnlyTimeDisplay() {
    ReadOnlyTimeDisplay(entryVal = { 123456L })
}