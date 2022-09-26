package mx.com.emv.menotes.presentation.notes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mx.com.emv.menotes.data.MockData

class NotesViewModel: ViewModel() {
    private val _uiState = MutableLiveData<NotesUIState>()
    val uiState: LiveData<NotesUIState> = _uiState

    fun fetchNotes(){
        viewModelScope.launch {
            _uiState.value = NotesUIState.Loading(true)
            //delay(3000)
            _uiState.value = NotesUIState.Loading(false)
            //_uiState.value = NotesUIState.Error("Mensaje de error")
            _uiState.value = NotesUIState.Success(MockData.fakeNotes)
        }
    }
}