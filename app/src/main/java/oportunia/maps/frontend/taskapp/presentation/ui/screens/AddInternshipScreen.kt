package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.domain.model.Internship
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton

@Composable
fun AddInternshipScreen(
    navController: NavController,
    internshipLocationViewModel: InternshipLocationViewModel,
    locationCompanyViewModel: LocationCompanyViewModel
) {
    var internshipName by remember { mutableStateOf("") }
    val locationCompany by locationCompanyViewModel.tempLocation.collectAsState()
    val saveResult by internshipLocationViewModel.saveResult.collectAsState()



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Ícono grande de "práctica"
            Icon(
                imageVector = Icons.Default.WorkOutline,
                contentDescription = "Internship Icon",
                modifier = Modifier.size(96.dp),
                tint = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.add_internship_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Preparación para imagen (Cloudify)
            // locationCompany?.imageUrl?.let { imageUrl ->
            //     Image(
            //         painter = rememberAsyncImagePainter(imageUrl),
            //         contentDescription = null,
            //         modifier = Modifier
            //             .fillMaxWidth()
            //             .height(200.dp)
            //             .clip(RoundedCornerShape(8.dp))
            //     )
            //     Spacer(modifier = Modifier.height(16.dp))
            // }

            OutlinedTextField(
                value = internshipName,
                onValueChange = { internshipName = it },
                label = { Text(stringResource(id = R.string.internship)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth().navigationBarsPadding(),

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
                            locationCompany?.let {
                                internshipLocationViewModel.saveInternshipWithLocation(
                                    internship,
                                    it
                                )
                            }
                        }
                        navController.popBackStack()
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}



