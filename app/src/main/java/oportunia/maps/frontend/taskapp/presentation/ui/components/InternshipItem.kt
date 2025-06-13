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
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(company.imageProfile)
                        .crossfade(true)
                        .error(R.drawable.default_profile_company)
                        .fallback(R.drawable.default_profile_company)
                        .build(),
                    contentDescription = stringResource(R.string.profile_picture_content_description),
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
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

    val company = internship.locationCompany.company
    val internshipDetail = internship.internship.details
    val companyName = company.nameCompany
    val companyRating = company.ratingCompany
    val reason = internship.reason
    val score = internship.score

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
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(company.imageProfile)
                        .crossfade(true)
                        .error(R.drawable.default_profile_company)
                        .fallback(R.drawable.default_profile_company)
                        .build(),
                    contentDescription = stringResource(R.string.profile_picture_content_description),
                    modifier = Modifier
                        .size(140.dp)
                        .clip(CircleShape)
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
                Text(
                    text = reason,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray
                )
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}