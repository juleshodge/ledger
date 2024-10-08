package com.concordia.canary.ledger.add_edit_weight.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import com.concordia.canary.ledger.WeightParams
import com.concordia.canary.ledger.core.domain.model.InputUnits
import com.concordia.canary.ledger.ui.theme.ResponsiveAppTheme
import com.concordia.canary.ledger.util.UiText
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun WeighValueEntry(
    modifier: Modifier = Modifier,
    viewModelParams: WeightParams
) {

    WeighValueContainer(
        modifier = modifier,
        weight = { viewModelParams.weightValue() },
        unitAbbreviation = { viewModelParams.weightUnits() },
        isWeightError = {
            !viewModelParams.weightValueValid()
        },
        weightErrorMessage = {
            viewModelParams.weightValueError()
        },
        weightValueUpdater = viewModelParams.weightValueUpdate,

        inputUnitsUpdater = viewModelParams.onUnitsChanged,

        availableWeightUnits = { viewModelParams.availableWeightUnits() },

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

    val verySmall = ResponsiveAppTheme.dimens.verySmall

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = weight(),
            onValueChange = { weightValueUpdater(it) },
            textStyle = TextStyle.Default.copy(
                textAlign = TextAlign.Center
            ),

            isError = isWeightError(),

            placeholder = {
                Text(
                    text = "           0.0",
                    textAlign = TextAlign.Center
                )


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
                if (unitAbbreviation().displayName.isBlank()) {
                    Text(text = "N/A")
                } else {
                    Text(text = unitAbbreviation().displayName)
                }

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
            }

        )

        Box(
            modifier = Modifier
                .border(
                    width = verySmall,
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
                                            inputUnitsUpdater(it)
                                            expandedUnits = false
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
fun PreviewWeightEntry(modifier: Modifier = Modifier) {
    WeighValueContainer(
        modifier = modifier,
        weight = { "" },
        unitAbbreviation = { InputUnits.KgUnits },
        isWeightError = { true },
        weightErrorMessage = { UiText.DynamicText("A error msg") },
        weightValueUpdater = {},
        availableWeightUnits = { InputUnits.entries },
        inputUnitsUpdater = {}
    )
}

