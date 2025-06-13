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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.InternshipCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationFlagState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestCreateState
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
    val context = LocalContext.current
    val requestCreateState by requestViewModel.requestCreateState.collectAsState()
    // Fetch the location company details and internships

    LaunchedEffect(locationCompanyId) {
        locationCompanyViewModel.selectLocationById(locationCompanyId)
        internshipLocationViewModel.loadInternshipsLocationsFlagByLocationId(locationCompanyId)
    }

    LaunchedEffect(requestCreateState) {
        when (requestCreateState) {
            is RequestCreateState.Error -> {
                val message = (requestCreateState as RequestCreateState.Error).message
                //Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
            is RequestCreateState.Success -> {
                //Toast.makeText(context, R.string.request_success_message.toString(), Toast.LENGTH_SHORT).show()
                internshipLocationViewModel.loadInternshipsLocationsFlagByLocationId(locationCompanyId)
            }
            else -> Unit
        }
    }


    val locationCompany by locationCompanyViewModel.selectedLocation.collectAsState()
    val internshipLocationFlagState by internshipLocationViewModel.internshipLocationStateFlag.collectAsState()


    Log.d("InternshipListStudentScreen", "Location company: $locationCompany")
    Log.d("InternshipListStudentScreen", "Internships state: $internshipLocationFlagState")

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
                    text = stringResource(id = R.string.internships_for, it.company.name),
                    modifier = Modifier.padding(16.dp)
                )


                // Mostrar loading general si se está haciendo una operación de update/delete
                if (internshipLocationFlagState is InternshipLocationFlagState.Loading ||
                    requestCreateState is RequestCreateState.Loading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    when (val state = internshipLocationFlagState) {
                        is InternshipLocationFlagState.Empty -> {
                            Text(
                                text = stringResource(id = R.string.no_internships_available),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        is InternshipLocationFlagState.Success -> {
                            if (state.internshipLocationsFlag.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                                ) {
                                    items(state.internshipLocationsFlag) { internshipLocation ->
                                        InternshipCard(
                                            internshipFlag = internshipLocation,
                                            onRequestClick = { internshipLocationRequest ->
                                                requestViewModel.createRequestOfInternshipLocationFlag(internshipLocationRequest)
                                            }
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = stringResource(id = R.string.no_internships_available),
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        is InternshipLocationFlagState.Error -> {
                            Text(
                                text = stringResource(id = R.string.error_message, state.message),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(16.dp)
                            )
                        }

                        else -> {}
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
                        text = stringResource(id = R.string.back_button),
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(0.5f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

        } ?: Text(
            text = stringResource(id = R.string.location_details_unavailable),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}