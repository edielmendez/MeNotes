package mx.com.emv.menotes.data.remote.repository

import mx.com.emv.menotes.domain.model.Note

interface NoteRepository {
    suspend fun getAll(): Result<List<Note>>
    suspend fun getNote(id: Int): Result<Note>
}