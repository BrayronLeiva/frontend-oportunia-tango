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
import oportunia.maps.frontend.taskapp.data.datasource.internshiplocation.InternshipLocationProvider
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.InternshipLocation
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipCard
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipCardCompany
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipState

@Composable
fun InternshipListCompanyScreen(
    locationCompanyId: Long,
    navController: NavController,
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    paddingValues: PaddingValues
) {
    var refreshTrigger by remember { mutableStateOf(false) }

    LaunchedEffect(locationCompanyId, refreshTrigger) {
        locationCompanyViewModel.selectLocationById(locationCompanyId)
        internshipLocationViewModel.loadInternshipsByLocationId(locationCompanyId)
    }

    val locationCompany by locationCompanyViewModel.selectedLocation.collectAsState()
    val internshipState by internshipLocationViewModel.internships.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        locationCompany?.let { location ->
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Internships for ${location.company.name}",
                    modifier = Modifier.padding(16.dp)
                )

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
                                    InternshipCardCompany(internship = internship)
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
        } ?: Text(
            text = "Location details not available.",
            style = MaterialTheme.typography.bodyMedium
        )
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
            CustomButton(
                text = "Add",
                onClick = {
                    locationCompany?.let { location ->
                        val newInternshipLocation = InternshipLocation(
                            id = System.currentTimeMillis(), // Unique ID
                            location = location,
                            internship = Internship(System.currentTimeMillis(), "Sample internship")
                        )
                        InternshipLocationProvider.addInternshipLocation(newInternshipLocation)
                        Log.d("InternshipListStudentScreen", "Added internship: $newInternshipLocation")
                        refreshTrigger = !refreshTrigger // Toggle state to trigger recomposition
                    }
                },
                modifier = Modifier.weight(0.5f)
            )
        }
    }
}