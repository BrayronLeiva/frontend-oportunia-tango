package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.domain.model.Internship

@Composable
fun InternshipCard(internship: Internship) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = internship.details, style = MaterialTheme.typography.labelMedium)
            CustomButton(
                text = "Request",
                onClick = { /* Handle apply button click */ },
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}