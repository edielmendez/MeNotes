package mx.com.emv.menotes.data.remote.repository

import mx.com.emv.menotes.data.Note

interface NoteRepository {
    suspend fun getAll(): Result<List<Note>>
    suspend fun saveNote(note: Note)
    suspend fun getNote(id: Int): Result<Note>
    suspend fun deleteNote(id: Int)
    suspend fun getByRaw(word: String): Result<List<Note>>
}