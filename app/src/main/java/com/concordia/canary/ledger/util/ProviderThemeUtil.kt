package com.concordia.canary.ledger.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.concordia.canary.ledger.ui.theme.Dimensions
import com.concordia.canary.ledger.ui.theme.Orientation
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType
import com.concordia.canary.ledger.ui.theme.compactDimensions

@Composable
fun ProviderThemeUtil(
    dimensions: Dimensions,
    orientation: Orientation,
    windowSize: WindowSizeType,
    content: @Composable () -> Unit
) {
    val dimSet = remember { dimensions }
    val ortSet = remember { orientation }
    val windowSize = remember { windowSize }

    CompositionLocalProvider(
        LocalAppDimens provides dimSet,
        LocalOrtMode provides ortSet,
        LocalSize provides windowSize,
        content = content
    )
}

val LocalAppDimens = compositionLocalOf {
    compactDimensions
}

val LocalOrtMode = compositionLocalOf {
    Orientation.TALLER
}

val LocalSize = compositionLocalOf {
    WindowSizeType(WindowSize.Compact(100), WindowSize.Compact(200))
}