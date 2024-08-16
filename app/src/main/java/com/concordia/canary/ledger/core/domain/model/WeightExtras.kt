package com.concordia.canary.ledger.core.domain.model

enum class WeightExtras(val displayName: String) {
    Shoes("Shoes"),
    Boots("Boots"),
    Jacket("Jacket"),
    FullClothes("Clothes"),
    Fed("Fed");

    companion object {
        fun stringToList(weightExtras: String): List<WeightExtras> {
            if (weightExtras.isBlank()) {
                return emptyList()
            }
            val split: List<String> = weightExtras.split(",")

            val matching =
                split.map { str -> WeightExtras.entries.first { it -> it.name == str.trim() } }

            return matching;
        }
    }
}