package oportunia.maps.frontend.taskapp.presentation.ui.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes

@Composable
fun LocationCompanyCard(locationCompany: LocationCompany, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = locationCompany.company.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            InfoText(label = stringResource(id = R.string.description), value = locationCompany.company.description)
            InfoText(label = stringResource(id = R.string.history), value = locationCompany.company.history)
            InfoText(label = stringResource(id = R.string.mission), value = locationCompany.company.mision)
            InfoText(label = stringResource(id = R.string.vision), value = locationCompany.company.vision)
            InfoText(label = stringResource(id = R.string.contact), value = locationCompany.contact.toString())
            CustomButton(
                text = stringResource(id = R.string.rate_button),
                onClick = {
                    navController.navigate(
                        NavRoutes.RateCompany.createRoute(locationCompany.company.id!!)
                    )
                }
            )
        }
    }
}
@Composable
fun InfoText(label: String, value: String) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 8.dp)) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}
