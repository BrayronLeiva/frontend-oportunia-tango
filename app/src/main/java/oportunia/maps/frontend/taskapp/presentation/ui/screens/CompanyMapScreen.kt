package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.Company
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel

@Composable
fun CompanyMapScreen(
    navController: NavHostController,
    locationCompanyViewModel: LocationCompanyViewModel,
    companyViewModel: CompanyViewModel,
    paddingValues: PaddingValues,
    onLogout: () -> Unit
) {
    val costaRica = LatLng(9.7489, -83.7534)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(costaRica, 10f)
    }

    LaunchedEffect(Unit) {
        companyViewModel.getLoggedCompany()
    }

    var isAddingMarker by remember { mutableStateOf(false) }

    val locationCompanies by locationCompanyViewModel.locationList.collectAsState()
    val loggedCompany by companyViewModel.loggedCompany.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                if (isAddingMarker) {
                    addNewLocation(latLng, loggedCompany!!, locationCompanyViewModel)
                    isAddingMarker = false
                }
            }
        ) {
            locationCompanies.forEach { locationCompany ->
                val isOwner = loggedCompany?.id == locationCompany.company.id

                Marker(
                    state = MarkerState(position = locationCompany.location),
                    title = locationCompany.company.name,
                    snippet = stringResource(id = R.string.snippet_click_internships),
                    icon = if (isOwner) null else BitmapDescriptorFactory.defaultMarker(
                        BitmapDescriptorFactory.HUE_YELLOW
                    ),
                    onClick = {
                        if (isOwner) {
                            navController.navigate(
                                NavRoutes.InternshipListCompany.createRoute(
                                    locationCompany.id!!
                                )
                            )
                        }
                        true
                    }
                )
            }
        }

        FloatingActionButton(
            onClick = { isAddingMarker = !isAddingMarker },
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(
                    start =16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 16.dp)
                .zIndex(1f)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(id = R.string.add_marker_description)
            )
        }

        /*

        CustomButton(
            onClick = { onLogout() },
            text = stringResource(id = R.string.logout_button),
            modifier = Modifier
                .width(160.dp)
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )


         */

        if (isAddingMarker) {
            Text(
                text = stringResource(id = R.string.tap_to_add_marker),
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(
                    start = 16.dp,
                    bottom = paddingValues.calculateBottomPadding() + 16.dp
                )
                ,
                color = Color.White,
                style = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

fun addNewLocation(latLng: LatLng, company: Company, viewModel: LocationCompanyViewModel) {
    viewModel.addNewLocation(latLng, company)
}