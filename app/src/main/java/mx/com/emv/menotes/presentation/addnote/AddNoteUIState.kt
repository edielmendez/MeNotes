package mx.com.emv.menotes.presentation.addnote

import mx.com.emv.menotes.domain.model.Note

sealed class AddNoteUIState{
    data class Loading(val value: Boolean): AddNoteUIState()
    data class Success(val message: String): AddNoteUIState()
    data class Error(val error:String): AddNoteUIState()
    data class ObtainedNote(val note: Note): AddNoteUIState()
}
