package com.msayeh.notes.feature_note.domain.use_case

import com.msayeh.notes.feature_note.domain.model.InvalidNoteException
import com.msayeh.notes.feature_note.domain.model.Note
import com.msayeh.notes.feature_note.domain.repository.NoteRepository

class AddNote(private val repository: NoteRepository) {

    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note) {
        if (note.title.isBlank()) {
            throw InvalidNoteException("The title must have a value.")
        }
        if (note.content.isBlank()) {
            throw InvalidNoteException("The content must have a value.")
        }
        repository.insertOrUpdateNote(note)
    }
}