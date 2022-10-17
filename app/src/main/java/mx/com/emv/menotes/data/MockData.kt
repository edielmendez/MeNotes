package mx.com.emv.menotes.data

import mx.com.emv.menotes.domain.model.Note

object MockData {
    val fakeNotes = listOf(
        Note(
            id = 1,
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            id = 2,
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            id = 3,
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            id = 4,
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            id = 5,
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            id = 6,
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            id = 7,
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        )
    )
}

enum class Importance(val value: Int){
    LOW(1), MEDIUM(2), HIGH(3)
}