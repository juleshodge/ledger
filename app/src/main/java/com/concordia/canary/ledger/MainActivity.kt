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
import androidx.hilt.navigation.compose.hiltViewModel

import dagger.hilt.android.AndroidEntryPoint

import com.concordia.canary.ledger.add_edit_weight.presentation.PreviewWeightPane
import com.concordia.canary.ledger.add_edit_weight.presentation.WeightPane
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.WeightEntryViewModel
import com.concordia.canary.ledger.ui.theme.LedgerTheme
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.ui.theme.WindowSize
import com.concordia.canary.ledger.ui.theme.WindowSizeType
import com.concordia.canary.ledger.ui.theme.rememberWindowSizeType
import com.concordia.canary.ledger.util.UiText
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.domain.model.WeightExtras
import com.concordia.canary.ledger.add_edit_weight.presentation.state.RecentWeightsState
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.RecentWeightsViewModel


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val windowSizeType = rememberWindowSizeType()
            LedgerTheme(windowSizeType) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val viewModel: WeightEntryViewModel = hiltViewModel()
                    val recentWeightsViewModel: RecentWeightsViewModel = hiltViewModel();

                    val ort = ResponsiveAppTheme.ortMode;

                    val params = WeightEditParams(
                        onSavePressed = viewModel::onSavePressed,
                        selectedExtras = { viewModel.entryState.selectedExtras },
                        weightValueValid = { viewModel.entryState.weightValueValid },
                        updateExtraSelection = viewModel::onWeightExtraSelected,
                        weightUnits = { viewModel.entryState.weightUnits },
                        weightValue = { viewModel.entryState.weightValue },
                        weightValueError = { viewModel.entryState.weightValueError },
                        weightValueUpdate = viewModel::onWeightValueChanged,
                        onUnitsChanged = viewModel::onUnitsChanged,
                        availableWeightUnits = { InputUnits.entries.toList() },
                        weightNotesValue = { viewModel.entryState.weightNotesValue },
                        weightNotesValueUpdate = viewModel::onWeightValueNotesChanged
                    )

                    val recentParams =
                        RecentWeightParams(
                            loadRecentWeights = recentWeightsViewModel::loadRecentWeights,
                            recentState = { recentWeightsViewModel.recentsState })

                    WeightPane(
                        modifier = Modifier.padding(innerPadding),
                        viewModelParams = params,
                        recentItems = recentParams,
                        ort
                    )
                }
            }
        }
    }
}

data class WeightEditParams(
    val onSavePressed: () -> Unit,
    val weightValue: () -> String,
    val weightValueUpdate: (newWeightValue: String) -> Unit,
    val weightValueError: () -> UiText?,
    val weightUnits: () -> InputUnits,
    val weightValueValid: () -> Boolean,
    val selectedExtras: () -> List<WeightExtras>,
    val updateExtraSelection: (WeightExtras, Boolean) -> Unit,
    val onUnitsChanged: (InputUnits) -> Unit,
    val availableWeightUnits: () -> List<InputUnits>,
    val weightNotesValue: () -> String,
    val weightNotesValueUpdate: (newNotesValue: String) -> Unit,
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