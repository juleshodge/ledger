package com.concordia.canary.ledger

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.add_edit_weight.domain.model.Weight

import dagger.hilt.android.AndroidEntryPoint

import com.concordia.canary.ledger.add_edit_weight.presentation.PreviewWeightPane
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType
import com.concordia.canary.ledger.ui.theme.rememberWindowSizeType
import com.concordia.canary.ledger.util.UiText
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.core.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.presentation.state.RecentWeightsState
import com.concordia.canary.ledger.util.Navigation
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeightEvent
import com.concordia.canary.ledger.weight_trends.presentation.state.WeightTrendsState


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeType = rememberWindowSizeType()
            LedgerTheme(windowSizeType) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    Navigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

data class TrendWeightParams(
    val trendState: () -> WeightTrendsState,
    val loadTrendWeights: () -> Unit,
    val eventSendHandler: (TrendWeightEvent) -> Unit
)

data class WeightParams(
    val weightValue: () -> String,
    val weightValueUpdate: (newWeightValue: String) -> Unit,
    val weightValueError: () -> UiText?,
    val weightUnits: () -> InputUnits,
    val onUnitsChanged: (InputUnits) -> Unit,
    val weightValueValid: () -> Boolean,
    val availableWeightUnits: () -> List<InputUnits>,
)

data class WeightAddParams(
    val onSavePressed: () -> Unit,
    val weightParams: WeightParams,
    val selectedExtras: () -> List<WeightExtras>,
    val updateExtraSelection: (WeightExtras, Boolean) -> Unit,
    val noteEditParams: NoteEditParams,
    val weightObsTimeValue: () -> Long,
    val weightObsTimeValueUpdate: (newObsTime: Long) -> Unit
)

data class NoteEditParams(
    val weightNotesValue: () -> String,
    val weightNotesValueUpdate: (newNotesValue: String) -> Unit
)

data class WeightEditParams(
    val onUpdate: () -> Unit,
    val onDelete: (weightId: Long) -> Unit,
    val onLoadWeightEntry: (weightId: Long) -> Unit,
    val weightParams: WeightParams,
    val selectedExtras: () -> List<WeightExtras>,
    val updateExtraSelection: (WeightExtras, Boolean) -> Unit,
    val noteEditParams: NoteEditParams,
    val weightObsTimeValue: () -> Long
)


data class RecentWeightParams(
    val loadRecentWeights: () -> Unit,
    val recentState: () -> RecentWeightsState
)


@Preview(showBackground = true)
@Composable
        /*    @Preview(
                showBackground = true,
                device = "spec:width=448dp,height=998dp,dpi=480,isRound=false,chinSize=0dp,orientation=portrait"
            )*/
        /*@Preview(
            showBackground = true,
            device = "spec:width=800dp,height=1280dp,dpi=320,isRound=false,chinSize=0dp,orientation=landscape"
        )*/

fun ActivityPreview() {
    val windowSizeType = WindowSizeType(WindowSize.Medium(448), WindowSize.Medium(998))
    LedgerTheme(windowSizeType) {
        PreviewWeightPane()
    }
}