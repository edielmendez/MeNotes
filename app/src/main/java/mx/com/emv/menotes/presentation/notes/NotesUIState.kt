package mx.com.emv.menotes.presentation.notes

import mx.com.emv.menotes.data.Note

sealed class NotesUIState{
    data class Loading(val value: Boolean): NotesUIState()
    data class Success(val notes:List<Note>): NotesUIState()
    object Empty: NotesUIState()
    data class Error(val error:String): NotesUIState()
}
