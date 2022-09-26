package mx.com.emv.menotes.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import mx.com.emv.menotes.data.Importance
import mx.com.emv.menotes.data.Note

@Entity(tableName = "tb_notes")
data class NoteDTO(
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "importance") val importance: Int?
){
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    fun toNote() = Note(id = id ?: 0, title = title ?: "", description = description ?: "", importance = importance ?: Importance.HIGH.value)
}
