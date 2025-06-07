package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.domain.model.LocationCompany
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.SaveInternshipResult


@Composable
fun AddInternshipScreen(
    navController: NavController,
    internshipLocationViewModel: InternshipLocationViewModel,
    locationCompanyViewModel: LocationCompanyViewModel
) {
    var internshipName by remember { mutableStateOf("") }
    val locationCompany by locationCompanyViewModel.tempLocation.collectAsState()
    val saveResult by internshipLocationViewModel.saveResult.collectAsState()

    LaunchedEffect(saveResult) {
        if (saveResult is SaveInternshipResult.Success) {
            navController.popBackStack()
            // Optionally reset the state to Idle if user comes back to this screen
            internshipLocationViewModel.resetSaveResult()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = internshipName,
            onValueChange = { internshipName = it },
            label = { Text(stringResource(id = R.string.internship)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CustomButton(
                text = stringResource(id = R.string.back_button),
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            CustomButton(
                text = stringResource(id = R.string.add_button),
                onClick = {
                    if (internshipName.isNotBlank()) {
                        val internship = Internship(
                            id = null,
                            details = internshipName
                        )
                        locationCompany?.let { locationCompany ->
                            internshipLocationViewModel.saveInternshipWithLocation(
                                internship,
                                locationCompany
                            )
                        }
                        //navController.popBackStack()
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}