package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CompanyMapScreen(navController: NavHostController, paddingValues: PaddingValues) {
    val costaRica = LatLng(9.7489, -83.7534)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(costaRica, 10f)
    }

    // States
    var isAddingMarker = remember { mutableStateOf(false) }  // Flag to enable marker adding
    var markers = remember { mutableStateOf<List<LatLng>>(emptyList()) } // List of added markers

    Box(modifier = Modifier.fillMaxSize()) {
        // GoogleMap with onMapClick to add a marker if in marker-adding mode
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                if (isAddingMarker.value) {
                    // Add the clicked location to the list of markers
                    markers.value += latLng
                }
            }
        ) {
            // Display all markers in the list
            markers.value.forEach { markerPosition ->
                Marker(
                    state = MarkerState(position = markerPosition),
                    title = "Company Location",
                    snippet = "Click for Internships",
                    onClick = {
                        navController.navigate("internships/${markerPosition.latitude}/${markerPosition.longitude}")
                        true
                    }
                )
            }
        }


        // Plus button to enable adding markers
        FloatingActionButton(
            onClick = {
                isAddingMarker.value = !isAddingMarker.value // Toggle the marker adding mode
            },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Marker"
            )
        }

        // Button to remove all markers
        Button(
            onClick = { markers.value = emptyList() }, // Clear all markers
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Text("Clear All Markers")
        }

        // Information text to show current mode
        if (isAddingMarker.value) {
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