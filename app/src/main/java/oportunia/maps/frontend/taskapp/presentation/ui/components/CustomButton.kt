package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.presentation.ui.theme.Black
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan

@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 350.dp,
    enabled: Boolean = true
) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkCyan,
            contentColor = Black
        ),
        enabled = enabled
    ) {
        Text(text = text, color = Color.DarkGray)
    }
}


@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    width: Dp = 350.dp,
    backgroundColor: Color,
    textColor: Color
) {
    Spacer(modifier = Modifier.height(16.dp))
    Button(
        onClick = onClick,
        modifier = modifier
            .width(width)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        )
    ) {
        Text(text = text, color = textColor)
    }
}
