package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany

@Composable
fun LocationCompanyCard(locationCompany: LocationCompany) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = locationCompany.company.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoText(label = "Description:", value = locationCompany.company.description)
            InfoText(label = "History:", value = locationCompany.company.history)
            InfoText(label = "Mission:", value = locationCompany.company.mision)
            InfoText(label = "Vision:", value = locationCompany.company.vision)
            InfoText(label = "Contact:", value = locationCompany.contact.toString())
        }
    }
}

@Composable
fun InfoText(label: String, value: String) {
    Column(modifier = Modifier.padding(bottom = 8.dp)) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}