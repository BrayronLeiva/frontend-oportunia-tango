package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import oportunia.maps.frontend.taskapp.data.datasource.locationcompany.LocationCompanyProvider
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes

@Composable
fun CompanyMapScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val costaRica = LatLng(9.7489, -83.7534)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(costaRica, 10f)
    }

    // State for toggling marker-adding mode
    var isAddingMarker by remember { mutableStateOf(false) }

    // Fetch all location companies
    val locationCompanies by remember { mutableStateOf(LocationCompanyProvider.findAllLocationCompanies()) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Google Map
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                if (isAddingMarker) {
                    addNewLocation(latLng)
                    isAddingMarker = false
                }
            }
        ) {
            // Display existing location companies as markers
            locationCompanies.forEach { locationCompany ->
                Marker(
                    state = MarkerState(position = locationCompany.location),
                    title = locationCompany.company.name,
                    snippet = "Click for Internships",
                    onClick = {
                        navController.navigate(NavRoutes.InternshipListCompany.createRoute(locationCompany.id))
                        true
                    }
                )
            }
        }

        // Plus button to enable adding markers
        FloatingActionButton(
            onClick = { isAddingMarker = !isAddingMarker },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Marker")
        }

        // Info text when adding markers
        if (isAddingMarker) {
            Text(
                text = "Tap on the map to add markers",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp),
                color = Color.White,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

/**
 * Adds a new location to `LocationCompanyProvider` using the first `LocationCompany` as a template.
 */
fun addNewLocation(latLng: LatLng) {
    val baseCompany = LocationCompanyProvider.findAllLocationCompanies().firstOrNull() ?: return

    val newId = (LocationCompanyProvider.findAllLocationCompanies().maxOfOrNull { it.id } ?: 0) + 1

    val newLocationCompany = baseCompany.copy(
        id = newId,
        location = latLng
    )

    LocationCompanyProvider.addLocationCompany(newLocationCompany)
}