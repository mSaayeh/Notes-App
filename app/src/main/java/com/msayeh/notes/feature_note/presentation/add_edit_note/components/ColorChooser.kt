package com.msayeh.notes.feature_note.presentation.add_edit_note.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.msayeh.notes.feature_note.presentation.add_edit_note.AddEditNoteViewModel

@Composable
fun ColorChooser(
    colors: List<Color>,
    modifier: Modifier = Modifier,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    onColorClicked: (Color) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        colors.forEach { color ->
            val isChosenColor = viewModel.noteColor.value == color.toArgb()

            val sizeState = if (isChosenColor) 47.dp else 50.dp
            val size = animateDpAsState(targetValue = sizeState, animationSpec = tween(durationMillis = 500))

            val borderWidthState = if (isChosenColor) 3.dp else 0.dp
            val borderWidth = animateDpAsState(targetValue = borderWidthState, animationSpec = tween(durationMillis = 500))

            val borderColorState = if (isChosenColor) Color.Black else Color.Transparent
            val borderColor = animateColorAsState(targetValue = borderColorState, animationSpec = tween(durationMillis = 500))

            Box(
                modifier = Modifier
                    .size(size.value)
                    .shadow(15.dp, CircleShape)
                    .clip(CircleShape)
                    .background(color)
                    .border(
                        width = borderWidth.value,
                        color = borderColor.value,
                        CircleShape
                    )
                    .clickable {
                        onColorClicked(color)
                    }
            )
        }
    }
}