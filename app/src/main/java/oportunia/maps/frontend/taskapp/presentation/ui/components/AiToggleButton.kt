package oportunia.maps.frontend.taskapp.presentation.ui.components


import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan
import oportunia.maps.frontend.taskapp.R

@Composable
fun AiToggleButton(
    isAiEnabled: Boolean,
    onToggle: () -> Unit
) {
    val backgroundColor by animateColorAsState(
        if (isAiEnabled) Color.Red else DarkCyan,
        label = "backgroundColor"
    )
    //val icon = if (isAiEnabled) Icons.Filled.Lightbulb else Icons.Filled.LightbulbOutline
    val text = if (isAiEnabled) stringResource(id = R.string.deactivate) else "AI"

    Button(
        onClick = onToggle,
        colors = ButtonDefaults.buttonColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        //Icon(icon, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}
