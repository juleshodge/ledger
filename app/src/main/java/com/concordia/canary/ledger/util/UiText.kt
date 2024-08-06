package com.concordia.canary.ledger.util

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DynamicText(val str: String) : UiText();
    class StringResource(@StringRes val resId: Int, vararg val args: Any) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DynamicText -> str
            is StringResource -> stringResource(id = resId, args)
        }
    }
}