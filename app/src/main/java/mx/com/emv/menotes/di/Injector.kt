package mx.com.emv.menotes.di

import android.content.Context
import mx.com.emv.menotes.data.local.NotesDataBase
import mx.com.emv.menotes.data.local.repository.NoteLocalDataSource

object Injector {
    private lateinit var localDataSource: NoteLocalDataSource

    fun setup(context: Context){
        localDataSource = NoteLocalDataSource(context)
    }

    fun provideDataSource() = localDataSource
}