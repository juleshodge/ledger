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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.concordia.canary.ledger.NoteEditParams
import com.concordia.canary.ledger.RecentWeightParams
import com.concordia.canary.ledger.TrendWeightParams
import com.concordia.canary.ledger.WeightAddParams
import com.concordia.canary.ledger.WeightEditParams
import com.concordia.canary.ledger.WeightParams
import com.concordia.canary.ledger.add_edit_weight.presentation.WeightEdit
import com.concordia.canary.ledger.add_edit_weight.presentation.WeightPane
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.RecentWeightsViewModel
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.WeightEditViewModel
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.WeightEntryViewModel
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.trend_settings_edit.presentation.TrendSettingsPane
import com.concordia.canary.ledger.trend_settings_edit.presentation.viewmodel.TrendSettingsViewModel
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.weight_trends.domain.model.TrendWeightEvent
import com.concordia.canary.ledger.weight_trends.presentation.WeightTrendsPane
import com.concordia.canary.ledger.weight_trends.presentation.viewmodel.WeightTrendsViewModel

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val ort = ResponsiveAppTheme.ortMode;

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.WeightTrendPane.route,
        modifier = modifier
    ) {
        composable(ScreenRoutes.TrendSettingsPane.route) {
            val viewModel: TrendSettingsViewModel = hiltViewModel()

            TrendSettingsPane(
                getSelectableUnits = viewModel::getAvailSelectableUnits,
                trendSelectionUpdate = viewModel::updateTrendUnitSelection,
                selectedUnits = { viewModel.state.sel },

                onDaysBackChange = viewModel::onDaysBackChange,
                daysBackVal = { viewModel.state.daysBack.toString() },
                onSettingsNavBack = {
                    navController.navigate(ScreenRoutes.WeightTrendPane.route) {
                        popUpTo(0)
                    }
                },
                ort = ort
            )
        }

        composable(ScreenRoutes.WeightTrendPane.route) {

            val viewModel: WeightTrendsViewModel = hiltViewModel()

            LocalLifecycleOwner.current.lifecycleScope.launch {
                viewModel.trendEvents.collectLatest { value ->
                    when (value) {
                        TrendWeightEvent.NavToAdd -> {
                            navController.navigate(ScreenRoutes.WeightAddPane.route) {
                                popUpTo(0)
                            }
                        }

                        is TrendWeightEvent.NavToEdit -> {

                            val finalDest = ScreenRoutes.WeightEditPane.route.replace(
                                oldValue = "{weightId}", newValue = "${value.weightId}"
                            )

                            navController.navigate(
                                finalDest,
                            ) {
                                popUpTo(0)
                            }
                        }

                        TrendWeightEvent.NavToTrendSettings -> {
                            navController.navigate(ScreenRoutes.TrendSettingsPane.route) {
                                popUpTo(0)
                            }
                        }
                    }
                }
            }


            viewModel.trendEvents

            val params = TrendWeightParams(
                trendState = { viewModel.trendsState },
                loadTrendWeights = viewModel::loadRecentTrendWeights,
                eventSendHandler = viewModel::sendTrendEvent
            )

            WeightTrendsPane(
                trendsStateParams = params
            )
        }

        composable(
            (ScreenRoutes.WeightEditPane.route),
            arguments = listOf(navArgument("weightId") { type = NavType.StringType })
        ) { backStackEntry ->
            val weightId = backStackEntry.arguments?.getString("weightId")

            val context = LocalContext.current
            val weightEditViewModel: WeightEditViewModel = hiltViewModel()

            val params = WeightEditParams(
                onUpdate = weightEditViewModel::onUpdate,
                onLoadWeightEntry = weightEditViewModel::onLoadWeightEntry,
                selectedExtras = { weightEditViewModel.editState.selectedExtras },
                weightParams = WeightParams(

                    weightValueValid = { weightEditViewModel.editState.weightValueValid },
                    weightUnits = { weightEditViewModel.editState.weightUnits },
                    weightValue = { weightEditViewModel.editState.weightValue },
                    weightValueError = { weightEditViewModel.editState.weightValueError },
                    weightValueUpdate = weightEditViewModel::onWeightValueChanged,
                    onUnitsChanged = weightEditViewModel::onUnitsChanged,
                    availableWeightUnits = { InputUnits.entries.toList() },
                ),

                updateExtraSelection = weightEditViewModel::onWeightExtraSelected,

                noteEditParams = NoteEditParams(
                    weightNotesValue = {
                        if (weightEditViewModel.editState.weightNotesValue.isNullOrEmpty()) "" else weightEditViewModel.editState.weightNotesValue.toString()
                    }, weightNotesValueUpdate = weightEditViewModel::onWeightValueNotesChanged
                ),
                onDelete = weightEditViewModel::onDelete,

                weightObsTimeValue = { weightEditViewModel.editState.weightObsTime },
            )


            //TODO this needs to be shared
            LocalLifecycleOwner.current.lifecycleScope.launch {
                weightEditViewModel.generalEventFlow.collectLatest { value ->
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

                        is GeneralEvent.WeightAdded -> TODO()
                        is GeneralEvent.WeightUpdated -> TODO()
                    }
                }
            }


            WeightEdit(weightEditParams = params,
                ort = ort,
                weightId = { weightId!!.toLong() },
                weightAddNavBack = {
                    navController.navigate(ScreenRoutes.WeightTrendPane.route) {
                        popUpTo(0)
                    }
                })
        }


        composable(ScreenRoutes.WeightAddPane.route) {
            val viewModel: WeightEntryViewModel = hiltViewModel()
            val recentWeightsViewModel: RecentWeightsViewModel = hiltViewModel();
            val context = LocalContext.current

            val params = WeightAddParams(
                onSavePressed = viewModel::onSavePressed,
                selectedExtras = { viewModel.entryState.selectedExtras },
                weightParams = WeightParams(
                    weightValueValid = { viewModel.entryState.weightValueValid },
                    weightUnits = { viewModel.entryState.weightUnits },
                    weightValue = { viewModel.entryState.weightValue },
                    weightValueError = { viewModel.entryState.weightValueError },
                    weightValueUpdate = viewModel::onWeightValueChanged,
                    onUnitsChanged = viewModel::onUnitsChanged,
                    availableWeightUnits = { InputUnits.entries.toList() },
                ),

                updateExtraSelection = viewModel::onWeightExtraSelected,
                noteEditParams = NoteEditParams(
                    weightNotesValue = { viewModel.entryState.weightNotesValue },
                    weightNotesValueUpdate = viewModel::onWeightValueNotesChanged
                ),
                weightObsTimeValue = { viewModel.entryState.weightObsTime },
                weightObsTimeValueUpdate = viewModel::onWeightObsTimeChange,
            )

            val recentParams =
                RecentWeightParams(loadRecentWeights = recentWeightsViewModel::loadRecentWeights,
                    recentState = { recentWeightsViewModel.recentsState })

            LocalLifecycleOwner.current.lifecycleScope.launch {
                viewModel.downStreamChan.collectLatest { value ->
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

                        is GeneralEvent.WeightAdded -> TODO()
                        is GeneralEvent.WeightUpdated -> TODO()
                    }
                }
            }

            WeightPane(viewModelParams = params,
                recentItems = recentParams,
                ort = ort,
                weightAddNavBack = {
                    navController.navigate(ScreenRoutes.WeightTrendPane.route) {
                        popUpTo(0)
                    }
                })
        }
    }
}

sealed class ScreenRoutes(val route: String, vararg params: String) {
    data object WeightAddPane : ScreenRoutes("weight_add/")
    data object TrendSettingsPane : ScreenRoutes("trend_settings/")
    data object WeightTrendPane : ScreenRoutes("weight_trend/")
    data object WeightEditPane : ScreenRoutes("weight_edit/{weightId}")
}