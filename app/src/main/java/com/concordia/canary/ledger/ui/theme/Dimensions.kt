package com.concordia.canary.ledger.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimensions(
    val small: Dp,
    val smallMedium: Dp,
    val medium: Dp,
    val mediumLarge: Dp,
    val large: Dp
)

val compactDimensions = Dimensions(
    small = 3.dp,
    smallMedium = 5.dp,
    medium = 8.dp,
    mediumLarge = 11.dp,
    large = 15.dp
)

val mediumDimensions = Dimensions(
    small = 5.dp,
    smallMedium = 8.dp,
    medium = 11.dp,
    mediumLarge = 15.dp,
    large = 18.dp
)

val expandedDimensions = Dimensions(
    small = 8.dp,
    smallMedium = 11.dp,
    medium = 15.dp,
    mediumLarge = 20.dp,
    large = 25.dp
)