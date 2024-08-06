package com.concordia.canary.ledger.add_edit_weight.presentation.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Abc
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.concordia.canary.ledger.add_edit_weight.presentation.viewmodel.WeightEntryViewModel

@Composable
fun NotesEntry(viewModel: WeightEntryViewModel = hiltViewModel()) {

    NotesEntryContainer(
        notesVal = {
            viewModel.entryState.weightNotesValue
        },
        valueChangeHandler = viewModel::onWeightValueNotesChanged
    )
}

@Composable
fun NotesEntryContainer(
    notesVal: () -> String,
    valueChangeHandler: (String) -> Unit
) {

    OutlinedTextField(
        leadingIcon = { Icons.Filled.Abc },
        value = notesVal(),
        maxLines = 3,
        singleLine = false,
        onValueChange = valueChangeHandler,


    )
}

@Composable
@Preview
fun PreviewNotesEntryContainer() {
    NotesEntryContainer(notesVal = { "Some notes" }, valueChangeHandler = { TODO() })
}