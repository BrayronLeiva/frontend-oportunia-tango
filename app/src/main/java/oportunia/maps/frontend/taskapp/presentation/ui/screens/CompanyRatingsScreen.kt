package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.RatingCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RatingCompanyStudentViewModel

@Composable
fun CompanyRatingsScreen(
    navController: NavController,
    companyId: Long,
    ratingViewModel: RatingCompanyStudentViewModel,
    paddingValues: PaddingValues
) {
    val ratingList by ratingViewModel.ratingCompanyStudentList.collectAsState()

    LaunchedEffect(Unit) {
        ratingViewModel.findAllRatingCompanyStudents()
    }

    val filteredRatings = remember(ratingList, companyId) {
        ratingList.filter { it.company.id == companyId && it.type == TypeUser.COM }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.student_ratings_title),
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (ratingList.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (filteredRatings.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_ratings_available),
                    style = MaterialTheme.typography.bodyMedium
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(filteredRatings) { ratingEntry ->
                        RatingCard(ratingEntry)
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton(
                onClick = { navController.popBackStack() },
                text = stringResource(R.string.back_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            )
        }
    }
}