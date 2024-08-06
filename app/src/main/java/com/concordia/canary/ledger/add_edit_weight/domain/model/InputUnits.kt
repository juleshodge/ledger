package com.concordia.canary.ledger.add_edit_weight.domain.model

enum class InputUnits(
    val displayName: String,
    val displayLongName: String,
    val unitNumeric: Int
) {
    KgUnits("Kg", "Kilograms", 1),
    LbUnits("Lbs", "Imperial Pounds", 2)
}

