package com.msayeh.notes.feature_note.domain.use_case

import com.msayeh.notes.feature_note.domain.model.InvalidNoteException
import com.msayeh.notes.feature_note.domain.model.Note
import com.msayeh.notes.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank() && note.content.isBlank()) {
            throw InvalidNoteException("The note must have a value.")
        }
        repository.insertOrUpdateNote(
            note.copy(
                title = note.title.trim(),
                content = note.content.trim()
            )
        )
    }
}