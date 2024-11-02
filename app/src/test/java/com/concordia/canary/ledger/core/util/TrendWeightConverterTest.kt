package com.concordia.canary.ledger.core.util

import org.junit.Test

import org.junit.Assert.*

import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.TrendSelectedUnits
import com.concordia.canary.ledger.util.WeightConverter
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight

class TrendWeightConverterTest {
    private val realWeightConverter = WeightConverter()

    @Test
    fun givenKgWeightWithLbsPreferred_transformWeight_returnsLbTrendWeightValue() {
        val trendConverter = TrendWeightConverter(realWeightConverter)
        val otw = TrendWeight(
            1, 10.0, InputUnits.KgUnits, null,
            null, 1L, "", emptyList()
        )
        val convertedWeight = trendConverter.transformWeight(
            baseWeight = otw,
            preferredUnits = TrendSelectedUnits.ConvertTrendUnit(InputUnits.LbUnits)
        )

        assertTrue(convertedWeight.currentWeightUnits == InputUnits.LbUnits)
        assertTrue(convertedWeight.currentWeightValue == 22.0462)
    }
    @Test
    fun givenKgWeightWithLbsPreferred_transformWeight_returnsLbTrendWeight() {
        val trendConverter = TrendWeightConverter(realWeightConverter)
        val otw = TrendWeight(
            1, 10.0, InputUnits.KgUnits, null,
            null, 1L, "", emptyList()
        )
        val convertedWeight = trendConverter.transformWeight(
            baseWeight = otw,
            preferredUnits = TrendSelectedUnits.ConvertTrendUnit(InputUnits.LbUnits)
        )

        assertTrue(convertedWeight.currentWeightUnits == InputUnits.LbUnits)
        assertTrue(convertedWeight.originalWeightUnits == InputUnits.KgUnits)
    }

    @Test
    fun givenKgWeightWithLbsPreffered_transformWeightToLbs_returnsLbTrendWeightSameValue() {
        val trendConverter = TrendWeightConverter(realWeightConverter)
        val otw = TrendWeight(
            1, 10.0, InputUnits.LbUnits, null,
            null, 1L, "", emptyList()
        )
        val convertedWeight = trendConverter.transformWeight(
            baseWeight = otw,
            preferredUnits = TrendSelectedUnits.ConvertTrendUnit(InputUnits.LbUnits)
        )

        assertTrue(convertedWeight.currentWeightUnits == InputUnits.LbUnits)
        assertTrue(convertedWeight.originalWeightUnits == InputUnits.LbUnits)
        assertTrue(convertedWeight.currentWeightValue == otw.originalValue)
    }
}