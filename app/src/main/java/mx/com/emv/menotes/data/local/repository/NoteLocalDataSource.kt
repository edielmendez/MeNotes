package mx.com.emv.menotes.data.local.repository

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.data.local.NoteDTO
import mx.com.emv.menotes.data.local.NoteDao
import mx.com.emv.menotes.data.local.NotesDataBase

class NoteLocalDataSource(context: Context): NoteLocalRepository {
    private lateinit var dao: NoteDao

    init{
        val db = NotesDataBase.getInstance(context)
        db?.let {
            dao = it.noteDao()
        }
    }

    override suspend fun getAll() = withContext(Dispatchers.IO) {
        try {
            val notes = dao.getAll().map {
                it.toNote()
            }
            Result.success(notes)
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }

    override suspend fun saveNote(note: Note) = withContext(Dispatchers.IO) {
        dao.insertNote(NoteDTO(title = note.title, description = note.description, importance = note.importance))
    }

    override suspend fun getNote(id: Int) = withContext(Dispatchers.IO)  {
        try {
            Result.success(dao.getNote(id).toNote())
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }

    override suspend fun deleteNote(note: Note) = withContext(Dispatchers.IO) {
        val noteDto = NoteDTO(title = note.title, description = note.description, importance = note.importance)
        noteDto.id = note.id
        dao.insertNote(noteDto)
    }

}