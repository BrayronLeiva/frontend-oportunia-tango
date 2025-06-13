package oportunia.maps.frontend.taskapp.presentation.ui.components




import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun NextButtom(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 100.dp
) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .height(40.dp), // Altura estándar de botón
        shape = RoundedCornerShape(12.dp), // Bordes redondeados



    ) {
        Text(label)
    }
}



@Preview
@Composable
fun NextButtomPreview(){
    //NextButtom("Hello World")

}