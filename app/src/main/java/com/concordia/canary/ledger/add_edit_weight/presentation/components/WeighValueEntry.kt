package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.concordia.canary.ledger.add_edit_weight.domain.model.InputUnits
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.WeightEntryViewModel
import com.concordia.canary.ledger.util.UiText


@Composable
fun WeighValueEntry(
    modifier: Modifier = Modifier,

    viewModel: WeightEntryViewModel = hiltViewModel()
) {

    WeighValueContainer(

        weight = { viewModel.entryState.weightValue },
        unitAbbreviation = { viewModel.entryState.weightUnits },
        isWeightError = {
            !viewModel.entryState.weightValueValid
        },
        weightErrorMessage = {
            viewModel.entryState.weightValueError
        },
        weightValueUpdater = viewModel::onWeightValueChanged,

        inputUnitsUpdater = viewModel::onUnitsChanged,

        availableWeightUnits = { viewModel.entryState.availableUnits },

        )
}

@Composable
fun WeighValueContainer(
    modifier: Modifier = Modifier,
    weight: () -> String,
    unitAbbreviation: () -> InputUnits,
    isWeightError: () -> Boolean,
    weightErrorMessage: () -> UiText?,
    weightValueUpdater: (String) -> Unit,
    inputUnitsUpdater: (InputUnits) -> Unit,
    availableWeightUnits: () -> List<InputUnits>
) {

    var expandedUnits by remember {
        mutableStateOf(false)
    }

    Row() {
        OutlinedTextField(
            value = weight(),
            onValueChange = { weightValueUpdater(it) },
            textStyle = TextStyle.Default.copy(
                textAlign = TextAlign.End
            ),
            isError = isWeightError(),

            placeholder = {
                Text(text = "0.0")
            },
            leadingIcon = {
                Icon(imageVector = Icons.Outlined.MonitorWeight, contentDescription = null)
            },
            supportingText = {
                if (isWeightError() && weightErrorMessage() != null) {
                    Text(text = weightErrorMessage()!!.asString())
                }
            },
            suffix = {
                Text(text = unitAbbreviation().displayName)
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            trailingIcon = {
                IconButton(onClick = { expandedUnits = !expandedUnits }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }
            },

            )

        Box(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
        ) {
            Row(modifier = Modifier.align(Alignment.Center)) {

                DropdownMenu(

                    expanded = expandedUnits,
                    content = {

                        availableWeightUnits()
                            .forEach {
                                if (unitAbbreviation() == it) {
                                    DropdownMenuItem(
                                        trailingIcon = {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = "Selected"
                                            )
                                        },

                                        text = { Text(text = it.displayName) },
                                        onClick = {

                                        })
                                } else {
                                    DropdownMenuItem(
                                        text = { Text(text = it.displayName) },
                                        onClick = {
                                            inputUnitsUpdater(it);
                                            expandedUnits = false;
                                        })
                                }

                            }
                    },
                    onDismissRequest = { expandedUnits = false })
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewWeightEntry() {
    WeighValueContainer(
        weight = { "171.2" },
        unitAbbreviation = { InputUnits.KgUnits },
        isWeightError = { true },
        weightErrorMessage = { UiText.DynamicText("A error msg") },
        weightValueUpdater = {},
        availableWeightUnits = { InputUnits.entries },
        inputUnitsUpdater = {}
    )
}

