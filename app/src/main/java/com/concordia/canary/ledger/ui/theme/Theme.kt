package com.concordia.canary.ledger.ui.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Teal80,
    outline = Teal80,
    primaryContainer = Teal30,
    onPrimary = Teal20,
    inversePrimary = Teal40,
    onPrimaryContainer = Teal10,


    secondary = Orange80,
    secondaryContainer = Orange30,
    onSecondary = Orange20,
    onSecondaryContainer = Orange10,

    tertiary = Indigo80,
    tertiaryContainer = Indigo30
)

private val LightColorScheme = lightColorScheme(
    primary = Teal40,
    outline = Teal40,
    primaryContainer = Teal90,
    onPrimaryContainer = Teal10,
    inversePrimary = Teal80,
    onPrimary = Teal100,

    secondary = Orange40,
    secondaryContainer = Orange90,
    onSecondaryContainer = Orange10,
    onSecondary = Teal10,

    tertiary = Indigo40,
    tertiaryContainer = Indigo90
)

@Composable
fun LedgerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        /*dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }*/

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typographyCompact,
        content = content
    )
}