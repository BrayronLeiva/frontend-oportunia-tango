package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun StarRating(rating: Double, onRatingChanged: (Float) -> Unit) {
    Row {
        (1..5).forEach { star ->
            IconButton(onClick = { onRatingChanged(star.toFloat()) }) {
                Icon(
                    imageVector = if (rating >= star) Icons.Default.Star else Icons.Default.StarBorder,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}