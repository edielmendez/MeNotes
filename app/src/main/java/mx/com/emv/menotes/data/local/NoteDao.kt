package mx.com.emv.menotes.data.local

import androidx.room.*

@Dao
interface NoteDao {
    @Query("SELECT * from tb_notes")
    fun getAll(): List<NoteDTO>

    @Query("SELECT * from tb_notes WHERE id = :id")
    fun getNote(id: Int): NoteDTO

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: NoteDTO)

    @Delete
    fun deleteNote(note: NoteDTO)
}