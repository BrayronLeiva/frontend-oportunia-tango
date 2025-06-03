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
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel

@Composable
fun CompanyMapScreen(
    navController: NavHostController,
    locationCompanyViewModel: LocationCompanyViewModel,
    paddingValues: PaddingValues
) {
    val costaRica = LatLng(9.7489, -83.7534)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(costaRica, 10f)
    }

    var isAddingMarker by remember { mutableStateOf(false) }

    val locationCompanies by locationCompanyViewModel.locationList.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                if (isAddingMarker) {
                    addNewLocation(latLng, locationCompanyViewModel)
                    isAddingMarker = false
                }
            }
        ) {
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

        FloatingActionButton(
            onClick = { isAddingMarker = !isAddingMarker },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Marker")
        }

        CustomButton(
            onClick = { navController.navigate(NavRoutes.Login.ROUTE) },
            text = "Logout",
            modifier = Modifier
                .width(160.dp)
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

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

fun addNewLocation(latLng: LatLng, viewModel: LocationCompanyViewModel) {
    viewModel.addNewLocation(latLng)
}