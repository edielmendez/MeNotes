package mx.com.emv.menotes.presentation.addnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import mx.com.emv.menotes.data.remote.repository.NoteRepository
import mx.com.emv.menotes.domain.usecase.NoteUseCases

class AddNoteViewModel(private val useCases: NoteUseCases): ViewModel() {
    private val _uiState = MutableLiveData<AddNoteUIState>()
    val uiState: LiveData<AddNoteUIState> = _uiState

    fun getNote(id: Int){
        _uiState.value = AddNoteUIState.Loading(true)
        //Get note from db
        viewModelScope.launch {
            val response = useCases.getNote(id = id)
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