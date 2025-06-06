package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestViewModel

@Composable
fun InternshipListStudentScreen(
    locationCompanyId: Long,
    navController: NavController,
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    internshipViewModel: InternshipViewModel,
    requestViewModel: RequestViewModel,
    paddingValues: PaddingValues
) {
    // Fetch the location company details and internships
    LaunchedEffect(locationCompanyId) {
        locationCompanyViewModel.selectLocationById(locationCompanyId)
        //internshipViewModel.loadInternshipsByLocationId(locationCompanyId)
        internshipLocationViewModel.loadInternshipsLocationsByLocationId(locationCompanyId)
        //internshipViewModel.findAllInternships()
    }

    val locationCompany by locationCompanyViewModel.selectedLocation.collectAsState()
    val internshipLocationState by internshipLocationViewModel.internshipLocationState.collectAsState()
    //val internshipState by internshipViewModel.internshipState.collectAsState()
    //val internshipState by internshipLocationViewModel.internships.collectAsState()

    Log.d("InternshipListStudentScreen", "Location company: $locationCompany")
    Log.d("InternshipListStudentScreen", "Internships state: $internshipLocationState")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        //locationCompany?.let {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    //text = "Internships for ${it.company.name}",
                    text = "Internships for ${locationCompany?.company?.name}",
                    modifier = Modifier.padding(16.dp)
                )

                // Handle the different internship states
                when (val state = internshipLocationState) {
                    is InternshipLocationState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    }
                    is InternshipLocationState.Empty -> {
                        Text(
                            text = "No internships available.",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    is InternshipLocationState.Success -> {
                        if (state.internshipLocations.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.fillMaxWidth().padding(16.dp)
                            ) {
                                items(state.internshipLocations) { internshipLocation ->
                                    InternshipCard(
                                        internship = internshipLocation.internship,
                                        onRequestClick = { inter ->
                                            // Aquí llamás al ViewModel para crear la solicitud
                                            requestViewModel.createRequest(internshipLocation)
                                        }
                                    )
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
                    is InternshipLocationState.Error -> {
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

       // } ?: Text(
       //     text = "Location details not available.",
       //     style = MaterialTheme.typography.bodyMedium
        //)
    }
}