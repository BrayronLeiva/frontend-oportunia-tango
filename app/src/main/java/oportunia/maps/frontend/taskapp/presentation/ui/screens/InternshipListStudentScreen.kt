package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipCard
import oportunia.maps.frontend.taskapp.presentation.ui.components.LocationCompanyCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipState

@Composable
fun InternshipListStudentScreen(
    locationCompanyId: Long,
    navController: NavController,
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    paddingValues: PaddingValues
) {
    // Fetch the location company details and internships
    LaunchedEffect(locationCompanyId) {
        locationCompanyViewModel.selectLocationById(locationCompanyId)
        internshipLocationViewModel.loadInternshipsByLocationId(locationCompanyId)
    }

    val locationCompany by locationCompanyViewModel.selectedLocation.collectAsState()
    val internshipState by internshipLocationViewModel.internships.collectAsState()

    Log.d("InternshipListStudentScreen", "Location company: $locationCompany")
    Log.d("InternshipListStudentScreen", "Internships state: $internshipState")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        locationCompany?.let {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Internships for ${it.company.name}",
                    modifier = Modifier.padding(16.dp)
                )

                // Handle the different internship states
                when (val state = internshipState) {
                    is InternshipState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is InternshipState.Empty -> {
                        Text(
                            text = "No internships available.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    is InternshipState.Success -> {
                        if (state.internships.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth().padding(16.dp)
                            ) {
                                items(state.internships) { internship ->
                                    InternshipCard(internship = internship)
                                }
                            }
                        } else {
                            Text(
                                text = "No internships available.",
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                    is InternshipState.Error -> {
                        Text(
                            text = "Error: ${state.message}",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

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
                }
            }

        } ?: Text(
            text = "Location details not available.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}