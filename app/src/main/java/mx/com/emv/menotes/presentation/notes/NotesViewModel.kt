package mx.com.emv.menotes.presentation.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.com.emv.menotes.data.MockData
import mx.com.emv.menotes.data.local.repository.NoteLocalRepository

class NotesViewModel(private val repository: NoteLocalRepository): ViewModel() {
    private val _uiState = MutableLiveData<NotesUIState>()
    val uiState: LiveData<NotesUIState> = _uiState

    fun fetchNotes(){
        viewModelScope.launch {
            _uiState.value = NotesUIState.Loading(true)
            val result = repository.getAll()
            _uiState.value = NotesUIState.Loading(false)
            result.onSuccess {
                if (it.isNotEmpty()){
                    _uiState.value = NotesUIState.Success(it)
                }else{
                    _uiState.value = NotesUIState.Empty
                }
            }
            result.onFailure {
                _uiState.value = NotesUIState.Error(error = it.message.toString())
            }
        }
    }
}