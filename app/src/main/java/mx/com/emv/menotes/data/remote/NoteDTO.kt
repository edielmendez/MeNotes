package mx.com.emv.menotes.data.remote

import com.google.gson.annotations.SerializedName
import mx.com.emv.menotes.data.Importance
import mx.com.emv.menotes.data.Note

data class NoteDTO(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("description") var description: String? = null
){
    fun toNote() = Note(id = id ?: 0, title = title ?: "", description = description ?: "", importance =  Importance.HIGH.value)
}
