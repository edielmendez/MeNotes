package mx.com.emv.menotes.domain.usecase

import mx.com.emv.menotes.data.remote.repository.NoteRepository
import mx.com.emv.menotes.domain.model.Note

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Result<Note> = repository.getNote(id)
}