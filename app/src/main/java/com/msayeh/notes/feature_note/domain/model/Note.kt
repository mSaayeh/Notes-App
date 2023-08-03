package com.msayeh.notes.feature_note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.msayeh.notes.ui.theme.BabyBlue
import com.msayeh.notes.ui.theme.LightGreen
import com.msayeh.notes.ui.theme.RedOrange
import com.msayeh.notes.ui.theme.RedPink
import com.msayeh.notes.ui.theme.Violet

@Entity
data class Note(
    val title: String = "",
    val content: String = "",
    val timestamp: Long,
    val color: Int,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
) {
    companion object {
        val noteColors = listOf(
            RedOrange, LightGreen, Violet, BabyBlue, RedPink
        )
    }
}

class InvalidNoteException(message: String) : Exception(message)
