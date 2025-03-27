package oportunia.maps.frontend.taskapp.presentation.ui.components


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TitleSection(
    value: String
) {
    Spacer(modifier = Modifier.height(40.dp))
    Text(value, style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)
    Spacer(modifier = Modifier.height(40.dp))
}


@Preview
@Composable
fun TitleSectionPreview(){
    TitleSection("Hello World")

}