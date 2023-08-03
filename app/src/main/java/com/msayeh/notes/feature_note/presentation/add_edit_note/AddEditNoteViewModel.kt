package com.msayeh.notes.feature_note.presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.msayeh.notes.feature_note.domain.model.InvalidNoteException
import com.msayeh.notes.feature_note.domain.model.Note
import com.msayeh.notes.feature_note.domain.use_case.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _noteTitle = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter Title..."
        )
    )
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _noteContent = mutableStateOf(
        NoteTextFieldState(
            hint = "Enter Some Content..."
        )
    )
    val noteContent: State<NoteTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf(
        savedStateHandle.get<Int>("noteColor")?.let {
            if (it == -1) {
                Note.noteColors.random().toArgb()
            } else {
                savedStateHandle.get<Int>("noteColor")
            }
        } ?: Note.noteColors.random().toArgb()
    )
    val noteColor: State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentNoteId: Int? = null

    init {
        savedStateHandle.get<Int>("noteId")?.let { noteId ->
            viewModelScope.launch {
                noteUseCases.getNote(noteId)?.also { note ->
                    currentNoteId = note.id
                    _noteTitle.value = noteTitle.value.copy(
                        text = note.title,
                        isHintVisible = note.title.isBlank()
                    )
                    _noteContent.value = noteContent.value.copy(
                        text = note.content,
                        isHintVisible = note.content.isBlank()
                    )
                    _noteColor.value = note.color
                }
            }
        }
    }

    fun onEvent(addEditNoteEvent: AddEditNoteEvent) {
        when (addEditNoteEvent) {
            is AddEditNoteEvent.ColorChange -> {
                _noteColor.value = addEditNoteEvent.color
            }

            is AddEditNoteEvent.ContentFocusChange -> {
                if (!addEditNoteEvent.focusState.isFocused && noteContent.value.text.isBlank()) {
                    _noteContent.value = noteContent.value.copy(
                        isHintVisible = true
                    )
                } else {
                    _noteContent.value = noteContent.value.copy(
                        isHintVisible = false
                    )
                }
            }

            is AddEditNoteEvent.ContentValueChange -> {
                _noteContent.value = noteContent.value.copy(
                    text = addEditNoteEvent.value
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = noteContent.value.text,
                                timestamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteException) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(e.message ?: "Invalid Note")
                        )
                    }
                }
            }

            is AddEditNoteEvent.TitleFocusChange -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !addEditNoteEvent.focusState.isFocused && _noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.TitleValueChange -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = addEditNoteEvent.value
                )
            }
        }
    }
}