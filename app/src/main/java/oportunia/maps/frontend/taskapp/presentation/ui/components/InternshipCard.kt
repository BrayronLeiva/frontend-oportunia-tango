package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationFlagDto
import oportunia.maps.frontend.taskapp.domain.model.Internship


@Composable
fun InternshipCard(
    internshipFlag: InternshipLocationFlagDto,
    onRequestClick: (InternshipDto) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = internshipFlag.internship.details,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.weight(1f))

            //CustomButton(
                //text = "Request",
                //onClick = { onRequestClick(internshipFlag.internship) },
                //modifier = Modifier.width(200.dp)
            //)

            Button(
                onClick = { onRequestClick(internshipFlag.internship) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (internshipFlag.requested) Color.Red else Color.Cyan,
                    contentColor = Color.White
                ),
                modifier = Modifier.width(150.dp)
            ) {
                androidx.compose.material.Text(
                    text = if (internshipFlag.requested) "Dismiss" else "Request",
                    fontWeight = FontWeight.Bold
                )
            }
        }

    }
}
