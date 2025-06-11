package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.presentation.ui.components.StarRating
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RatingCompanyStudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel

@Composable
fun RateStudentScreen(
    navController: NavController,
    ratingCompanyStudentViewModel: RatingCompanyStudentViewModel,
    companyViewModel: CompanyViewModel,
    studentViewModel: StudentViewModel,
    studentId: Long,
) {
    var rating by remember { mutableDoubleStateOf(0.0) }
    var comment by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        companyViewModel.getLoggedCompany()
        studentViewModel.findStudentById(studentId)
    }

    val selectedStudent by studentViewModel.selectedStudent.collectAsState()
    val selectedCompany by companyViewModel.loggedCompany.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Text(
                stringResource(R.string.rate_student_title),
                style = MaterialTheme.typography.headlineSmall
            )

            StarRating(rating = rating) { rating = it.toDouble() }

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text(stringResource(R.string.comment_label)) },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    ratingCompanyStudentViewModel.rate(
                        rating,
                        TypeUser.STU,
                        comment,
                        selectedCompany!!,
                        selectedStudent!!
                    )
                    navController.popBackStack()
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(stringResource(R.string.send_button))
            }
        }
    }
}