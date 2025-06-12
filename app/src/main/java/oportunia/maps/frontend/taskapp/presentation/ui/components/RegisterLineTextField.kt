package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.R

@Composable
fun RegisterLineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    height: Dp = 56.dp // Parámetro para definir la altura del TextField
) {
    Column(modifier = Modifier.fillMaxWidth()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Agregar margen vertical para mejor separación
        verticalAlignment = Alignment.CenterVertically // Alinea elementos al centro verticalmente
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            color = Color.Black,
            modifier = Modifier.padding(end = 8.dp) // Espaciado entre el texto y el TextField
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Color.LightGray) },
            modifier = Modifier
                .weight(1f) // Hace que ocupe el espacio restante
                .height(height),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.Transparent, // Quita el borde
                focusedBorderColor = Color.Transparent,   // Quita el borde al enfocar
                backgroundColor = Color.Transparent       // Fondo transparente
            ),
            singleLine = singleLine,
            shape = RoundedCornerShape(12.dp)
        )
    }

        Divider(color = Color.Gray, thickness = 1.dp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun RegisterLineTextField2(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    height: Dp = 56.dp
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            color = Color.Black,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp) // Espacio entre label y TextField
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(label, color = Color.LightGray) }, // Placeholder más sutil
            modifier = Modifier
                .fillMaxWidth()
                .height(height), // Altura más grande para mejor diseño
            singleLine = singleLine,
            shape = RoundedCornerShape(12.dp) // Bordes redondeados
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los campos
    }
}


@Preview
@Composable
fun RegisterLineTextFieldPreview() {
    var label by remember { mutableStateOf("") }
    RegisterLineTextField(

        value = stringResource(id = R.string.example),
        onValueChange = { label = it },
        label = stringResource(id = R.string.name),
        singleLine = true
    )

}