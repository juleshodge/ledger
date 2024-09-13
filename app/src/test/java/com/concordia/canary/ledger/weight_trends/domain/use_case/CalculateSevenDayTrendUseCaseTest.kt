package com.concordia.canary.ledger.weight_trends.domain.use_case

import org.junit.Test

import org.junit.Assert.*
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.util.WeightConverter
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight

class CalculateSevenDayTrendUseCaseTest {

    private val testCase = CalculateSevenDayTrendUseCase(WeightConverter())


    @Test
    fun givenList_invoke_returnsUnitsOfFirstMeasurement() {
        val trendWeight1 =
            TrendWeight(1L, 178.0, InputUnits.LbUnits, null, null, 500000L, "", emptyList())
        val trendWeight2 =
            TrendWeight(1L, 180.0, InputUnits.LbUnits, null, null, 520000L, "", emptyList())


        val result = testCase.invoke(listOf(trendWeight1, trendWeight2))

        assertTrue(result.averageUnits == trendWeight1.originalWeightUnits)
    }

    @Test
    fun givenSingle_invoke_returnsAverageOfSingleValue() {
        val trendWeight1 =
            TrendWeight(1L, 178.0, InputUnits.LbUnits, null, null, 500000L, "", emptyList())

        val result = testCase.invoke(listOf(trendWeight1))

        assertTrue(result.average == trendWeight1.originalValue)
    }

    @Test
    fun givenSingle_invoke_returnsDeltaOfSingleValue() {
        val trendWeight1 =
            TrendWeight(1L, 178.0, InputUnits.LbUnits, null, null, 500000L, "", emptyList())

        val result = testCase.invoke(listOf(trendWeight1))

        assertTrue(result.delta == 0.0)
    }

    @Test
    fun givenList_invoke_returnsDelta() {
        val trendWeight1 =
            TrendWeight(1L, 178.0, InputUnits.LbUnits, null, null, 500000L, "", emptyList())
        val trendWeight2 =
            TrendWeight(1L, 180.0, InputUnits.LbUnits, null, null, 520000L, "", emptyList())


        val result = testCase.invoke(listOf(trendWeight1, trendWeight2))

        assertTrue(result.delta == 2.0)
    }

    @Test
    fun givenList_invoke_returnsDelta_excludeResult8DaysOld() {
        val trendWeight1 =
            TrendWeight(1L, 178.0, InputUnits.LbUnits, null, null, 1726117200000, "", emptyList())
        val trendWeight2 =
            TrendWeight(1L, 180.0, InputUnits.LbUnits, null, null, 1726117210000, "", emptyList())

        val trendWeight3 =
            TrendWeight(1L, 185.0, InputUnits.LbUnits, null, null, 1725339600000, "", emptyList())

        val result = testCase.invoke(listOf(trendWeight1, trendWeight2, trendWeight3))

        assertTrue(result.delta == 2.0)
    }
}