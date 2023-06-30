package com.msayeh.notes.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent {
    data class TitleValueChange(val value: String): AddEditNoteEvent()
    data class TitleFocusChange(val focusState: FocusState): AddEditNoteEvent()
    data class ContentValueChange(val value: String): AddEditNoteEvent()
    data class ContentFocusChange(val focusState: FocusState): AddEditNoteEvent()
    data class ColorChange(val color: Int): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
