package com.concordia.canary.ledger.util

import android.widget.Toast
import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.concordia.canary.ledger.RecentWeightParams
import com.concordia.canary.ledger.TrendWeightParams
import com.concordia.canary.ledger.WeightEditParams
import com.concordia.canary.ledger.add_edit_weight.presentation.WeightPane
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.RecentWeightsViewModel
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.WeightEntryViewModel
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.weight_trends.presentation.WeightTrendsPane
import com.concordia.canary.ledger.weight_trends.presentation.viewmodel.WeightTrendsViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.WeightTrendPane.route,
        modifier = modifier
    ) {
        composable(ScreenRoutes.WeightTrendPane.route) {

            val viewModel: WeightTrendsViewModel = hiltViewModel()

            val params = TrendWeightParams(
                trendState = { viewModel.trendsState },
                loadTrendWeights = viewModel::loadRecentTrendWeights
            )

            WeightTrendsPane(
                trendsStateParams = params,
                weightAddClicked = {
                    navController.navigate(ScreenRoutes.WeightAddPane.route) {
                        popUpTo(0)
                    }
                })
        }

        composable(ScreenRoutes.WeightAddPane.route) {
            val viewModel: WeightEntryViewModel = hiltViewModel()
            val recentWeightsViewModel: RecentWeightsViewModel = hiltViewModel();
            val context = LocalContext.current
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
                weightNotesValueUpdate = viewModel::onWeightValueNotesChanged,
                weightObsTimeValue = { viewModel.entryState.weightObsTime },
                weightObsTimeValueUpdate = viewModel::onWeightObsTimeChange,
            )

            val recentParams =
                RecentWeightParams(loadRecentWeights = recentWeightsViewModel::loadRecentWeights,
                    recentState = { recentWeightsViewModel.recentsState })

            LocalLifecycleOwner.current.lifecycleScope.launch {
                viewModel.downStreamChan
                    .collectLatest { value ->
                        when (value) {
                            is GeneralEvent.Error -> {
                                Toast.makeText(
                                    context,
                                    value.errorMessage,
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }

                            is GeneralEvent.NavToRoute -> {
                                navController.navigate(ScreenRoutes.WeightTrendPane.route) {
                                    popUpTo(0)
                                }
                            }
                        }
                    }
            }

            WeightPane(
                viewModelParams = params,
                recentItems = recentParams,
                ort = ort,
                weightAddNavBack = {
                    navController.navigate(ScreenRoutes.WeightTrendPane.route) {
                        popUpTo(0)
                    }
                }
            )
        }
    }
}

sealed class ScreenRoutes(val route: String) {
    data object WeightAddPane : ScreenRoutes("weight_add/")
    data object WeightTrendPane : ScreenRoutes("weight_trend/")
}