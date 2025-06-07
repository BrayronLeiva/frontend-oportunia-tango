package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationState

@Composable
fun StudentMapScreen(navController: NavHostController, locationCompanyViewModel: LocationCompanyViewModel, paddingValues: PaddingValues) {
    val locationCompanies by locationCompanyViewModel.locationList.collectAsState()
    val locationState by locationCompanyViewModel.location.collectAsState()

    LaunchedEffect(Unit) {
        locationCompanyViewModel.findAllLocations()
    }

    var isRefreshing by remember { mutableStateOf(false) }

    // Update refreshing state based on task state
    isRefreshing = locationState is LocationState.Loading

    @OptIn(androidx.compose.material.ExperimentalMaterialApi::class)
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            locationCompanyViewModel.findAllLocations()
        }
    )

    val costaRica = LatLng(9.7489, -83.7534)  // Default map position (Costa Rica)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(costaRica, 10f)
    }

    val coroutineScope = rememberCoroutineScope() // Create a coroutine scope

    // Show loading while locations are being fetched (can replace with your data flow)
    if (locationCompanies.isEmpty()) {
        LoadingContent()
        return
    }

    Box(modifier = Modifier.fillMaxSize()) {
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
                        navController.navigate(NavRoutes.LocationCompanyDetail.createRoute(locationCompany.id!!))
                        true
                    }
                )
            }
        }

        // Custom zoom controls
        Column(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.zoomIn())
                    }
                }
            ) {
                Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Zoom In")
            }

            Spacer(modifier = Modifier.height(8.dp))

            IconButton(
                onClick = {
                    coroutineScope.launch {
                        cameraPositionState.animate(CameraUpdateFactory.zoomOut())
                    }
                }
            ) {
                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Zoom Out")
            }
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