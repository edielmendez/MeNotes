package mx.com.emv.menotes.data

import android.content.res.Resources
import mx.com.emv.menotes.R
import mx.com.emv.menotes.data.Note

object MockData {
    val fakeNotes = listOf(
        Note(
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        ),
        Note(
            title = "Este es el título de una nota",
            description = "Lorem ipsum es el texto que se usa habitualmente en diseño gráfico en demostraciones de tipografías o de borradores de diseño para probar el diseño visual antes de insertar el texto final.",
            importance = Importance.HIGH.value
        )
    )
}

enum class Importance(val value: Int){
    LOW(1), MEDIUM(2), HIGH(3)
}