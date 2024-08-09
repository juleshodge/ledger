package com.concordia.canary.ledger.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration

sealed class WindowSize(val size: Int) {
    data class Compact(val compactSize: Int) : WindowSize(compactSize)
    data class Medium(val mediumSize: Int) : WindowSize(mediumSize)
    data class Expanded(val largeSize: Int) : WindowSize(largeSize)
}

data class WindowSizeType(
    val width: WindowSize,
    val height: WindowSize
)

@Composable
fun rememberWindowSizeType(): WindowSizeType {
    val config = LocalConfiguration.current


    val width by remember(config) {
        mutableIntStateOf(config.screenWidthDp)
    }

    val height by remember(config) {
        mutableIntStateOf(config.screenHeightDp)
    }

    val windowWidthType = when {
        width < 600 -> WindowSize.Compact(width)
        width in 600..840 -> WindowSize.Compact(width)
        else -> WindowSize.Expanded(width)
    }

    val windowHeightType = when {
        height <= 480 -> WindowSize.Compact(height)
        height in 480..900 -> WindowSize.Medium(height)
        else -> WindowSize.Expanded(height)
    }

    return WindowSizeType(windowWidthType, windowHeightType)
}