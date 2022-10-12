package mx.com.emv.menotes.presentation.notes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.com.emv.menotes.data.remote.repository.NoteRepository

class NotesViewModel(private val repository: NoteRepository): ViewModel() {
    private val _uiState = MutableLiveData<NotesUIState>()
    val uiState: LiveData<NotesUIState> = _uiState

    init {
        fetchNotes()
    }

    fun fetchNotes(){
        viewModelScope.launch {
            _uiState.value = NotesUIState.Loading(true)
            Log.v("NotesViewModel", "fetchNotes")
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

    fun filterNotes(wordToSearch: String){
        //_uiState.value = NotesUIState.Loading(true)
        viewModelScope.launch {
            val result = repository.getByRaw(word = wordToSearch)
            result.onSuccess {
                if (it.isNotEmpty()){
                    _uiState.value = NotesUIState.Success(it)
                }else{
                    Log.v("filterNotes", "EMPTYYYYY")
                    _uiState.value = NotesUIState.Empty
                }
            }
            result.onFailure {
                _uiState.value = NotesUIState.Error(error = it.message.toString())
            }
        }
        /*val notesFiltered = MockData.fakeNotes.filter { it.title.uppercase().contains(wordtoSearch.uppercase()) || it.description.uppercase().contains(wordtoSearch.uppercase()) }
        _uiState.value = NotesUIState.Success(notesFiltered)*/
    }
}