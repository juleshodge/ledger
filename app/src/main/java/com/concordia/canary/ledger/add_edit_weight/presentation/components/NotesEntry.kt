package com.concordia.canary.ledger.add_edit_weight.presentation.components


import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.concordia.canary.ledger.R
import com.concordia.canary.ledger.WeightEditParams
import com.concordia.canary.ledger.util.UiText

@Composable
fun NotesEntry(
    modifier: Modifier = Modifier,
    viewModelParams: WeightEditParams
) {

    NotesEntryContainer(
        notesVal = {
            viewModelParams.weightNotesValue()
        },
        valueChangeHandler = viewModelParams.weightNotesValueUpdate,
        modifier = modifier
    )
}

@Composable
fun NotesEntryContainer(
    notesVal: () -> String,
    valueChangeHandler: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        modifier = modifier,
        value = notesVal(),
        placeholder = {
            Text(
                text = UiText.StringResource(R.string.weight_notes_placeholder_text).asString()
            )
        },
        maxLines = 1,
        singleLine = true,
        onValueChange = valueChangeHandler,
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewNotesEntryContainer(modifier: Modifier = Modifier) {
    NotesEntryContainer(
        modifier = modifier,
        notesVal = { "a notes value" },
        valueChangeHandler = { TODO() })

}