package com.msayeh.notes.feature_note.presentation.notes

import com.msayeh.notes.feature_note.domain.model.Note
import com.msayeh.notes.feature_note.domain.util.NoteOrder
import com.msayeh.notes.feature_note.domain.util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.DEFAULT_ORDER,
    val isOrderSectionVisible: Boolean = false
)
