package mx.com.emv.menotes.presentation.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.com.emv.menotes.data.MockData
import mx.com.emv.menotes.data.Note

class AddNoteViewModel: ViewModel() {
    private val _uiState = MutableLiveData<AddNoteUIState>()
    val uiState: LiveData<AddNoteUIState> = _uiState

    fun saveNote(note: Note?){
        viewModelScope.launch {
            _uiState.value = AddNoteUIState.Loading(true)
            //save information to db
            delay(2000)
            _uiState.value = AddNoteUIState.Loading(false)
            _uiState.value = AddNoteUIState.Success(message = "Nota agregada")
        }
    }

    fun deleteNote(id: Int){
        _uiState.value = AddNoteUIState.Loading(true)
        //Delete note from DB
        _uiState.value = AddNoteUIState.Loading(false)
        _uiState.value = AddNoteUIState.Success(message = "Nota eliminada")
    }

    fun getNote(id: Int){
        _uiState.value = AddNoteUIState.Loading(true)
        // TODO: Fetch from DB
        _uiState.value = AddNoteUIState.Loading(false)
        MockData.fakeNotes.find { it.id == id }?.let {
            _uiState.value = AddNoteUIState.ObtainedNote(it)
        }
    }
}