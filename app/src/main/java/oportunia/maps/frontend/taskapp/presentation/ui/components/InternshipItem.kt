package oportunia.maps.frontend.taskapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan

@Composable
fun InternshipItem(
    internship: InternshipLocation,
    onClick: (InternshipLocation) -> Unit
) {
    val company = internship.location.company
    val internshipDetail = internship.internship.details
    val companyName = company.name
    val companyRating = company.rating

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable { onClick(internship) },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(color = Color(0xFFE0F7FA), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(id = R.string.company),
                    tint =  Color.DarkGray,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = internshipDetail,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = companyName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = DarkCyan
                )
                Text(
                    text = "$companyRating ★",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}


@Composable
fun InternshipRecommendedCard(
    internship: InternshipLocationRecommendedDto,
    onClick: (InternshipLocationRecommendedDto) -> Unit) {
    // Obtener los datos de la compañía y la calificación
    val company = internship.locationCompany
    val internshipDetail = internship.internship.details
    val companyName = company.company.name
    val companyRating = company.company.rating

    Card(modifier = Modifier
        .fillMaxWidth().padding(vertical = 8.dp).clickable { onClick(internship) }) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.LocationOn, contentDescription = stringResource(id = R.string.company), modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(internshipDetail, fontWeight = FontWeight.ExtraBold)
                Text(companyName, fontWeight = FontWeight.Bold, color = DarkCyan)
                Text("$companyRating ★", color = Color.Gray)
                Text("${internship.score}", color = Color.Gray)
                Text("${internship.reason}", color = Color.Gray)
            }
        }
    }
}