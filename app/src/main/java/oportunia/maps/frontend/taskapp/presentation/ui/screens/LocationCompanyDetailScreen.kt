package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.LocationCompanyCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipViewModel
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        locationCompany?.let {
            LocationCompanyCard(locationCompany = it)
        } ?: Text(
            text = "Location details not available.",
            style = MaterialTheme.typography.bodyMedium
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                CustomButton(
                    text = "Back",
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.weight(0.5f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                CustomButton(
                    text = "Internships",
                    onClick = { navController.navigate(NavRoutes.InternshipListStudent.createRoute(locationCompanyId)) },
                    modifier = Modifier.weight(0.5f)
                )
            }
        }
    }
}