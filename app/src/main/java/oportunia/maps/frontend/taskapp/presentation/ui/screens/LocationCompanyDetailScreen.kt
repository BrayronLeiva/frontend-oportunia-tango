package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.LocationCompanyCard
import oportunia.maps.frontend.taskapp.presentation.ui.theme.DarkCyan
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel

@Composable
fun LocationCompanyDetailScreen(
    locationCompanyId: Long,
    navController: NavController,
    locationCompanyViewModel: LocationCompanyViewModel,
    paddingValues: PaddingValues
) {
    LaunchedEffect(locationCompanyId) {
        locationCompanyViewModel.selectLocationById(locationCompanyId)
    }

    val locationCompany by locationCompanyViewModel.selectedLocation.collectAsState()
    Log.d("LocationDetail", "Location company: $locationCompany")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = "Company Location Icon",
            modifier = Modifier.size(96.dp),
            tint = DarkCyan
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(id = R.string.company_information),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        locationCompany?.let {
            LocationCompanyCard(locationCompany = it, navController = navController)
            // Aquí puedes preparar para Cloudify: reemplazar por una imagen
            // Image(
            //     painter = rememberAsyncImagePainter(it.imageUrl), // o Cloudify URL
            //     contentDescription = null,
            //     modifier = Modifier
            //         .fillMaxWidth()
            //         .height(200.dp)
            //         .clip(RoundedCornerShape(8.dp))
            // )
        } ?: Text(
            text = stringResource(id = R.string.location_details_unavailable),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            CustomButton(
                text = stringResource(id = R.string.back_button),
                onClick = { navController.popBackStack() },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            CustomButton(
                text = stringResource(id = R.string.internships_button),
                onClick = {
                    navController.navigate(
                        NavRoutes.InternshipListStudent.createRoute(locationCompanyId)
                    )
                },
                modifier = Modifier.weight(1f)
            )
        }
    }
}
