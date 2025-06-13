package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SubtitleSection(
    label: String,
    sublabel: String = ""
) {
    Text(label, style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold))
    Text(sublabel, style = MaterialTheme.typography.subtitle1)
}


@Preview
@Composable
fun SubtitleSectionPreview(){
    SubtitleSection("Hello World")

}