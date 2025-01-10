package com.concordia.canary.ledger.weight_trends.domain.use_case

import java.util.Calendar
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.util.WeightConverter
import com.concordia.canary.ledger.weight_trends.domain.model.TrendStats
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeight

class CalculateSevenDayTrendUseCase(private val converter: WeightConverter) {

    private fun createCalendarInstance(targetVal: Long): Calendar {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = targetVal
        return calendar
    }

    private fun earliestStamp(refStart: Long, days: Int): Long {
        var curCalendar = createCalendarInstance(refStart)
        curCalendar.add(Calendar.DAY_OF_YEAR, -days)
        return curCalendar.timeInMillis;
    }

    operator fun invoke(applicableWeights: List<TrendWeight>): TrendStats {

        val earliestStamp = earliestStamp(System.currentTimeMillis(), 7)


        val sortedByDescending = applicableWeights
            .filter { v -> v.observationDate >= earliestStamp }
            .filter { w -> w.weightExtras.isEmpty() }
            .sortedByDescending { ap -> ap.observationDate }
        val size = sortedByDescending.size;

        if (size == 0) {
            return (TrendStats(
                average = 0.0,
                InputUnits.LbUnits,
                0.0
            ))
        }

        if (size == 1) {
            val trendWeight = sortedByDescending[0]
            return (TrendStats(
                average = trendWeight.originalValue!!,
                trendWeight.originalWeightUnits!!,
                0.0
            ))
        }

        val latest = sortedByDescending.last();
        val first = sortedByDescending.first();

        val firstUnits = first.originalWeightUnits!!

        val trendDelta =
            ((first.originalValue!! - converter.convert(
                latest.originalValue!!,
                latest.originalWeightUnits,
                firstUnits
            ))) % (first.originalValue);

        val vals = sortedByDescending.map {
            converter.convert(it.originalValue, it.originalWeightUnits, firstUnits)
        }
        val average = vals.average();

        return TrendStats(average = average, firstUnits, trendDelta)
    }
}