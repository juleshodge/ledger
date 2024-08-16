package com.concordia.canary.ledger.core.domain.model

enum class InputUnits(
    val displayName: String,
    val displayLongName: String,
    val unitNumeric: Int
) {
    KgUnits("Kg", "Kilograms", 1),
    LbUnits("Lbs", "Imperial Pounds", 2);

    companion object {
        fun fromNumeric(numeric: Int): InputUnits {
            return InputUnits.entries.first { u -> u.unitNumeric == numeric }
        }
    }
}