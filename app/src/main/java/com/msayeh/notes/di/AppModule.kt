package com.msayeh.notes.di

import android.app.Application
import androidx.room.Room
import com.msayeh.notes.feature_note.data.data_source.NoteDao
import com.msayeh.notes.feature_note.data.data_source.NoteDatabase
import com.msayeh.notes.feature_note.data.repository.NoteRepositoryImpl
import com.msayeh.notes.feature_note.domain.repository.NoteRepository
import com.msayeh.notes.feature_note.domain.use_case.AddNote
import com.msayeh.notes.feature_note.domain.use_case.DeleteNote
import com.msayeh.notes.feature_note.domain.use_case.GetNote
import com.msayeh.notes.feature_note.domain.use_case.GetNotes
import com.msayeh.notes.feature_note.domain.use_case.NoteUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNodeDao(noteDatabase: NoteDatabase): NoteDao {
        return noteDatabase.noteDao
    }

    @Provides
    @Singleton
    fun provideNoteRepository(noteDao: NoteDao): NoteRepository {
        return NoteRepositoryImpl(noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUseCases(noteRepository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(noteRepository),
            deleteNote = DeleteNote(noteRepository),
            addNote = AddNote(noteRepository),
            getNote = GetNote(noteRepository)
        )
    }
}