package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.presentation.ui.theme.Black
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan

@Composable
fun RatingFilterSelector(
    ratings: List<Double>,
    selectedRating: Double?,
    onRatingSelected: (Double?) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {},
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Choose the stars",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Row(horizontalArrangement = Arrangement.Center) {
                    ratings.forEach { rating ->
                        IconToggleButton(
                            checked = selectedRating != null && rating <= selectedRating,
                            onCheckedChange = {
                                onRatingSelected(if (selectedRating == rating) null else rating)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "$rating stars",
                                tint = if (selectedRating != null && rating <= selectedRating) Color(0xFFFFD700) else Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            onRatingSelected(null)
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkCyan,
                            contentColor = Black
                        )
                    ) {
                        Text("Clean")
                    }
                }
            }
        }
    )
}
