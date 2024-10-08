package com.concordia.canary.ledger.util

sealed class GeneralEvent() {
    data class NavToRoute(val rt: ScreenRoutes) : GeneralEvent()
    data class WeightAdded(val message: String) : GeneralEvent()
    data class WeightUpdated(val message: String) : GeneralEvent()
    data class Error(val errorMessage: String) : GeneralEvent()
}