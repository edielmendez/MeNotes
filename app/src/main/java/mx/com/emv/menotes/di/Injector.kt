package mx.com.emv.menotes.di

import android.content.Context
import mx.com.emv.menotes.data.remote.repository.NoteHttpDataSource
import mx.com.emv.menotes.data.remote.service.NotesApiClient
import mx.com.emv.menotes.domain.usecase.GetNote
import mx.com.emv.menotes.domain.usecase.GetNotes
import mx.com.emv.menotes.domain.usecase.NoteUseCases

object Injector {
    private lateinit var localDataSource: NoteHttpDataSource
    private lateinit var useCases: NoteUseCases

    fun setup(context: Context){
        localDataSource = NoteHttpDataSource(NotesApiClient.build())
        useCases = NoteUseCases(getNotes = GetNotes(repository = localDataSource), getNote = GetNote(repository = localDataSource))
    }

    fun provideDataSource() = localDataSource

    fun provideUseCases() = useCases
}