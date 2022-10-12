package mx.com.emv.menotes.di

import android.content.Context
import mx.com.emv.menotes.data.local.NotesDataBase
import mx.com.emv.menotes.data.local.repository.NoteLocalDataSource
import mx.com.emv.menotes.data.remote.repository.NoteHttpDataSource
import mx.com.emv.menotes.data.remote.service.NotesApiClient

object Injector {
    private lateinit var localDataSource: NoteHttpDataSource

    fun setup(context: Context){
        localDataSource = NoteHttpDataSource(NotesApiClient.build())
    }

    fun provideDataSource() = localDataSource
}