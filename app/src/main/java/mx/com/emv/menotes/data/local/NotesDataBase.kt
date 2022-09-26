package mx.com.emv.menotes.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NoteDTO::class], version = 1, exportSchema = false)
abstract class NotesDataBase: RoomDatabase() {
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DB_NAME = "ME_NOTES_ROOM.db"
        private var INSTANCE: NotesDataBase? = null

        fun getInstance(context: Context): NotesDataBase {
            if (INSTANCE == null) {
                synchronized(NotesDataBase::class.java) {
                    INSTANCE = Room.databaseBuilder(context, NotesDataBase::class.java, DB_NAME)
                        .build()
                }
            }
            return INSTANCE ?: throw Exception("ComicDataBase !=null")
        }

        fun destroyInstance() {
            INSTANCE = null
        }
    }
}