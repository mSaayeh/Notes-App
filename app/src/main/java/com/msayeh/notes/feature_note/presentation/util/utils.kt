package com.msayeh.notes.feature_note.presentation.util

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.unit.LayoutDirection


operator fun PaddingValues.plus(paddingValues: PaddingValues): PaddingValues {
    return PaddingValues(
        top = calculateTopPadding() + paddingValues.calculateTopPadding(),
        bottom = calculateBottomPadding() + paddingValues.calculateBottomPadding(),
        start = calculateStartPadding(LayoutDirection.Ltr) + paddingValues.calculateStartPadding(
            LayoutDirection.Ltr
        ),
        end = calculateEndPadding(LayoutDirection.Ltr) + paddingValues.calculateEndPadding(
            LayoutDirection.Ltr
        )
    )
}
