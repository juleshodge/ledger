package com.concordia.canary.ledger.add_edit_weight.presentation.components

import java.util.Calendar

import android.app.TimePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ManageHistory
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

fun getNewTimeStamp(
    calendar: Calendar,
    hours: Int,
    minutes: Int,
    entryValUpdate: (Long) -> Unit
): Calendar {

    val calendarRet = Calendar.getInstance()
    calendarRet[Calendar.HOUR_OF_DAY] = hours
    calendarRet[Calendar.MINUTE] = minutes
    entryValUpdate(calendarRet.timeInMillis)
    return calendar
}

fun setCalendarInstance(calendar: Calendar, entryVal: Long): Calendar {
    calendar.timeInMillis = entryVal
    return calendar
}

@Composable
fun TimeSpecificationEntry(
    modifier: Modifier = Modifier,
    entryVal: () -> Long,
    entryValUpdate: (Long) -> Unit
) {
    val mContext = LocalContext.current


    var cal = setCalendarInstance(Calendar.getInstance(), entryVal())

    val mHour = cal[Calendar.HOUR_OF_DAY]
    val mMinute = cal[Calendar.MINUTE]


    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, selectedHour: Int, selectedMinute: Int ->
            cal = getNewTimeStamp(cal, selectedHour, selectedMinute, entryValUpdate)
        },
        mHour, mMinute, false
    )

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
            onClick = { mTimePickerDialog.show() }
        ) {
            Icon(imageVector = Icons.TwoTone.ManageHistory, contentDescription = "Time")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTimeSpecificationEntry() {
    TimeSpecificationEntry(entryVal = { 123456L }, entryValUpdate = {})
}