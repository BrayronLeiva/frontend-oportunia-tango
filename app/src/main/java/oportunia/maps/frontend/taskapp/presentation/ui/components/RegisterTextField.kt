package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.R

@Composable
fun RegisterTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    singleLine: Boolean = true,
    height: Dp = 56.dp,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
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
            shape = RoundedCornerShape(12.dp), // Bordes redondeados
            keyboardOptions = keyboardOptions,
            visualTransformation = visualTransformation
        )
        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre los campos
    }
}


@Preview
@Composable
fun RegisterTextFieldPreview(){
    RegisterTextField(
        value = stringResource(id = R.string.example),
        onValueChange = {},
        label = stringResource(id = R.string.name),
        singleLine = true
    )

}