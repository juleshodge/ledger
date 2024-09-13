package com.concordia.canary.ledger.weight_trends.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.text.DecimalFormat
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.weight_trends.domain.model.TrendStats

@Composable
fun TrendStatsDisplay(stats: TrendStats?) {

    val twoDecPlaces = DecimalFormat("##.##")
    val twoDecPlacesHundreds = DecimalFormat("###.##")
    Card(
        modifier = Modifier
            .width(225.dp)
    ) {
        Column(
            modifier = Modifier.padding(ResponsiveAppTheme.dimens.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "Past 7 Days", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(text = "Change (%)", style = MaterialTheme.typography.bodySmall)
                if (stats == null) {
                    Text(text = twoDecPlaces.format(0))
                } else {
                    if(stats.delta > 0) {

                    }
                    Text(text = twoDecPlaces.format(stats.delta))
                }
            }

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (stats == null) {
                    Text(
                        text = "Average",
                        style = MaterialTheme.typography.bodySmall
                    )
                } else {
                    Text(
                        text = "Average (${stats.averageUnits.displayName})",
                        style = MaterialTheme.typography.bodySmall
                    )
                }


                if (stats == null) {
                    Text(text = twoDecPlacesHundreds.format(0))
                } else {
                    Text(text = twoDecPlacesHundreds.format(stats.average))
                }
            }
        }
    }
}


//@Preview(showBackground = true)
@Composable
@Preview(
    showBackground = true,
    device = "spec:width=998dp,height=448dp,dpi=480,isRound=false,chinSize=0dp,orientation=landscape"
)
fun PreviewTrendStatsDisplay() {
    val stats = TrendStats(78.74, InputUnits.LbUnits, 3.81)
    TrendStatsDisplay(stats)
}