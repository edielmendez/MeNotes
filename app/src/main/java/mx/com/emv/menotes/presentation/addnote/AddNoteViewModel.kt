package mx.com.emv.menotes.presentation.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.com.emv.menotes.data.Note
import mx.com.emv.menotes.data.remote.repository.NoteRepository

class AddNoteViewModel(private val repository: NoteRepository): ViewModel() {
    private val _uiState = MutableLiveData<AddNoteUIState>()
    val uiState: LiveData<AddNoteUIState> = _uiState

    fun saveNote(note: Note){
        viewModelScope.launch {
            _uiState.value = AddNoteUIState.Loading(true)
            //save information to db
            repository.saveNote(note)
            _uiState.value = AddNoteUIState.Loading(false)
            _uiState.value = AddNoteUIState.Success(message = "Nota agregada")
        }
    }

    fun deleteNote(id: Int){
        _uiState.value = AddNoteUIState.Loading(true)
        //Delete note from DB
        viewModelScope.launch {
            repository.deleteNote(id)
            _uiState.value = AddNoteUIState.Loading(false)
            _uiState.value = AddNoteUIState.Success(message = "Nota eliminada")
        }
    }

    fun getNote(id: Int){
        _uiState.value = AddNoteUIState.Loading(true)
        //Get note from db
        viewModelScope.launch {
            val response = repository.getNote(id = id)
            _uiState.value = AddNoteUIState.Loading(false)
            response.onSuccess {
                _uiState.value = AddNoteUIState.ObtainedNote(it)
            }
            response.onFailure {
                _uiState.value = AddNoteUIState.Error(error = it.message.toString())
            }
        }
    }
}