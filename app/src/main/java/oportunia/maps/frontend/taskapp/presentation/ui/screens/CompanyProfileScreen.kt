package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.ProfileInfoCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RatingCompanyStudentViewModel


@Composable
fun CompanyProfileScreen(
    companyViewModel: CompanyViewModel,
    ratingCompanyStudentViewModel: RatingCompanyStudentViewModel,
    navController: NavController,
    onLogOut: () -> Unit
) {
    LaunchedEffect(Unit) {
        companyViewModel.getLoggedCompany()
        ratingCompanyStudentViewModel.findAllRatingCompanyStudents()
    }

    val companyState by companyViewModel.companyState.collectAsState()
    val ratingList by ratingCompanyStudentViewModel.ratingCompanyStudentList.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (val state = companyState) {
            is CompanyState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is CompanyState.Empty -> {
                Text(
                    text = stringResource(R.string.no_company_info_available),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            is CompanyState.Success -> {
                val company = state.company

                val companyRatings = ratingList.filter {
                    it.company.id == company.id && it.type == TypeUser.COM
                }
                val averageRating = if (companyRatings.isNotEmpty()) {
                    companyRatings.map { it.rating }.average()
                } else {
                    0.0
                }

                Spacer(modifier = Modifier.height(24.dp))

                Image(
                    painter = painterResource(id = R.drawable.oportunia_maps),
                    contentDescription = stringResource(R.string.profile_picture_content_description),
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = company.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.rating_format, averageRating),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFA500),
                    modifier = Modifier.clickable {
                        navController.navigate(NavRoutes.CompanyRatings.createRoute(company.id))
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                ProfileInfoCard(
                    title = stringResource(R.string.mission),
                    value = company.mision
                )
                ProfileInfoCard(
                    title = stringResource(R.string.vision),
                    value = company.mision
                )
                ProfileInfoCard(
                    title = stringResource(R.string.corporate_culture),
                    value = company.corporateCultur
                )
                ProfileInfoCard(
                    title = stringResource(R.string.contact),
                    value = company.contact.toString()
                )

                Spacer(modifier = Modifier.height(32.dp))

                CustomButton(
                    onClick = { onLogOut() },
                    text = stringResource(R.string.logout_button),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }

            is CompanyState.Error -> {
                Text(
                    text = stringResource(R.string.error_message, state.message),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            CompanyState.Failure -> {
                Text(text = "")
            }
        }
    }
}