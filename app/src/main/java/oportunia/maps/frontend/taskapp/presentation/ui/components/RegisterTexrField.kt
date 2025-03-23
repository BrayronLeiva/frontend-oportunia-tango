package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp) // Espacio entre label y TextField
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Color.LightGray) }, // Placeholder más sutil
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp), // Altura más grande para mejor diseño
            singleLine = singleLine,
            shape = RoundedCornerShape(12.dp) // Bordes redondeados
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los campos
    }
}


@Preview
@Composable
fun RegisterTextFieldPreview(){
    RegisterTextField(
        value = "Ejemplo",
        onValueChange = {},
        label = "Nombre",
        singleLine = true
    )

}