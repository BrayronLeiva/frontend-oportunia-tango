package oportunia.maps.frontend.taskapp.presentation.ui.components




import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NextButton(
    label: String
) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = {},
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(label)
    }
}


@Preview
@Composable
fun NextButtonPreview(){
    NextButton("Hello World")

}