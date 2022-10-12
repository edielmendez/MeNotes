package mx.com.emv.menotes.data.remote.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.data.remote.service.NotesApiClient

class NoteHttpDataSource(
    private val serviceApi: NotesApiClient.ServiceApi
): NoteRepository {
    override suspend fun getAll() = withContext(Dispatchers.IO) {
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
}