package mx.com.emv.menotes.data.remote.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.data.local.repository.NoteLocalRepository
import mx.com.emv.menotes.data.remote.service.NotesApiClient
import okhttp3.Dispatcher

class NoteHttpDataSource(
    private val serviceApi: NotesApiClient.ServiceApi
): NoteLocalRepository {
    override suspend fun getAll() = withContext(Dispatchers.IO) {
        Log.v("NotesViewModel", "NoteHttpDataSource getAll")
        try {
            val response = serviceApi.getAll()
            if (response.isSuccessful){
                val notes = response.body()?.notes?.map {
                    it.toNote()
                } ?: emptyList()
                Result.success(notes)
            }else{
                val error = response.errorBody()?.string()
                Result.failure(Exception(error))
            }
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }

    override suspend fun saveNote(note: Note) {
        TODO("Not yet implemented")
    }

    override suspend fun getNote(id: Int) = withContext(Dispatchers.IO) {
        try {
            val response = serviceApi.getNote(id)
            if (response.isSuccessful){
                val note = response.body()?.toNote() ?: Note(id = 0, title = "", description = "", importance = 1)
                Result.success(note)
            }else{
                val error = response.errorBody()?.string()
                Result.failure(Exception(error))
            }
        }catch (exception: Exception){
            Result.failure(exception)
        }
    }

    override suspend fun deleteNote(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getByRaw(word: String): Result<List<Note>> {
        TODO("Not yet implemented")
    }
}