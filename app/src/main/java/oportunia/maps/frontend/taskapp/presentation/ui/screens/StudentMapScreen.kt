package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import oportunia.maps.frontend.taskapp.data.datasource.locationcompany.LocationCompanyProvider
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes

@Composable
fun StudentMapScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val locationCompanies = LocationCompanyProvider.findAllLocationCompanies() // Get list of location companies

    val costaRica = LatLng(9.7489, -83.7534)  // Default map position (Costa Rica)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(costaRica, 10f)
    }

    // Show loading while locations are being fetched (can replace with your data flow)
    if (locationCompanies.isEmpty()) {
        LoadingContent()
        return
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // Display markers for each location company
        locationCompanies.forEach { locationCompany ->
            Marker(
                state = MarkerState(position = locationCompany.location),
                onClick = {
                    // Navigate to the location details screen when a marker is clicked
                    navController.navigate(NavRoutes.LocationCompanyDetail.createRoute(locationCompany.id))
                    true
                }
            )
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.semantics {
                contentDescription = "Loading locations"
            }
        )
    }
}