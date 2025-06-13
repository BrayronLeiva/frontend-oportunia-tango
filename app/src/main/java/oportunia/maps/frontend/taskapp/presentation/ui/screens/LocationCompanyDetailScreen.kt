package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.LocationCompanyCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel

@Composable
fun LocationCompanyDetailScreen(
    locationCompanyId: Long,
    navController: NavController,
    locationCompanyViewModel: LocationCompanyViewModel,
    paddingValues: PaddingValues
) {
    LaunchedEffect(locationCompanyId) {
        locationCompanyViewModel.selectLocationById(locationCompanyId)
    }

    val locationCompany by locationCompanyViewModel.selectedLocation.collectAsState()
    Log.d("LocationDetail", "Location company: $locationCompany")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.company_information),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        locationCompany?.let {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(it.company.imageProfile)
                    .crossfade(true)
                    .error(R.drawable.default_profile_company)
                    .fallback(R.drawable.default_profile_company)
                    .build(),
                contentDescription = stringResource(R.string.profile_picture_content_description),
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
            )
            LocationCompanyCard(locationCompany = it, navController = navController)

        } ?: Text(
            text = stringResource(id = R.string.location_details_unavailable),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            CustomButton(
                text = stringResource(id = R.string.back_button),
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomButton(
                text = stringResource(id = R.string.internships_button),
                onClick = {
                    navController.navigate(
                        NavRoutes.InternshipListStudent.createRoute(locationCompanyId)
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
