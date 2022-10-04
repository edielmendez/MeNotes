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

    @Update
    fun updateNote(note: NoteDTO)

    @Query("DELETE FROM tb_notes WHERE id = :id")
    fun deleteNote(id: Int)

    @Query("SELECT * FROM tb_notes WHERE (description LIKE '%' || :word || '%') OR (title LIKE '%' || :word || '%')")
    fun getByRaw(word: String): List<NoteDTO>
}