package com.msayeh.notes.feature_note.presentation.add_edit_note

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.msayeh.notes.feature_note.domain.model.Note
import com.msayeh.notes.feature_note.presentation.add_edit_note.components.ColorChooser
import com.msayeh.notes.feature_note.presentation.add_edit_note.components.TransparentTextField
import com.msayeh.notes.feature_note.presentation.util.plus
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "AddEditNoteScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel()
) {
    val noteTitle = viewModel.noteTitle.value
    val noteContent = viewModel.noteContent.value
    var colorState = viewModel.noteColor.value
    val color =
        animateColorAsState(
            targetValue = Color(colorState),
            animationSpec = tween(durationMillis = 500)
        )

    val snackbarState = remember { SnackbarHostState() }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save Note")
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarState, modifier = Modifier) }
    ) { paddingValues ->
        LaunchedEffect(key1 = true) {
            viewModel.eventFlow.collectLatest {
                when (it) {
                    is UiEvent.ShowSnackbar -> {
                        snackbarState.showSnackbar(it.message)
                    }

                    is UiEvent.SaveNote -> {
                        navController.navigateUp()
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color.value)
                .padding(paddingValues + PaddingValues(16.dp))
        ) {
            ColorChooser(
                Note.noteColors,
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                colorState = it.toArgb()
                viewModel.onEvent(AddEditNoteEvent.ColorChange(colorState))
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                value = noteTitle.text,
                hint = noteTitle.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.TitleValueChange(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.TitleFocusChange(it))
                },
                isHintVisible = noteTitle.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.headlineLarge,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentTextField(
                value = noteContent.text,
                hint = noteContent.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.ContentValueChange(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ContentFocusChange(it))
                },
                isHintVisible = noteContent.isHintVisible,
                singleLine = false,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}
