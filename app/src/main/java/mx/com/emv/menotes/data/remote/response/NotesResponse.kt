package mx.com.emv.menotes.data.remote.response

import com.google.gson.annotations.SerializedName
import mx.com.emv.menotes.data.remote.NoteDTO

data class NotesResponse(
    @SerializedName("folio") val folio: String? = null,
    @SerializedName("message") val message: String? = null,
    @SerializedName("notes") val notes: List<NoteDTO>? = emptyList()
)
