package mx.com.emv.menotes.domain.model

data class Note(
    val id: Int = 0,
    val title: String,
    val description: String,
    val importance: Int
)
