package com.concordia.canary.ledger.core.presentation.components

import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Date
import java.time.temporal.ChronoUnit

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun DateTimeDisplay(timeStamp: Long) {
    val now = Instant.now()

    val stampInstant = Instant.ofEpochMilli(timeStamp)
    val between = ChronoUnit.DAYS.between(stampInstant, now)
    val hoursBetween = ChronoUnit.HOURS.between(stampInstant, now)
    val minutesBetween = ChronoUnit.MINUTES.between(stampInstant, now)
    val dte = Date.from(stampInstant)

    if (between == 0L) {
        if (hoursBetween <= 6) {
            if (hoursBetween <= 1) {
                Text(text = "$minutesBetween minutes ago")
                return
            }
            Text(text = "$hoursBetween Hours ago")
            return
        }
        val formatter = SimpleDateFormat("HH:mm:ss", java.util.Locale.US)
        val final = formatter.format(dte)
        Text(text = final)
        return
    }

    if (between == 1L) {
        val formatter = SimpleDateFormat("HH:mm:ss", java.util.Locale.US)
        val final = formatter.format(dte)
        Text(text = "Yesterday $final")
        return
    }

    val formatter = SimpleDateFormat("MM dd yy HH:mm:ss", java.util.Locale.US)
    val final = formatter.format(dte)
    Text(text = final)
}