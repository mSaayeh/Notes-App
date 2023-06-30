package com.msayeh.notes.feature_note.domain.use_case

import androidx.lifecycle.MutableLiveData
import com.msayeh.notes.feature_note.domain.model.Note
import com.msayeh.notes.feature_note.domain.repository.NoteRepository
import com.msayeh.notes.feature_note.domain.util.NoteOrder
import com.msayeh.notes.feature_note.domain.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(private val repository: NoteRepository) {

    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNotes().map { notes ->
            when (noteOrder) {
                is NoteOrder.Title -> notes.sortedBy { it.title.lowercase() }
                is NoteOrder.Date -> notes.sortedBy { it.timestamp }
                is NoteOrder.Color -> notes.sortedBy { it.color }
            }
        }.map { notes ->
            if (noteOrder.orderType == OrderType.Descending) {
                notes.reversed()
            } else {
                notes
            }
        }
    }
}