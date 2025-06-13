package oportunia.maps.frontend.taskapp.presentation.ui.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
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
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel


@Composable
fun RegisterStudentSecond(
    navController: NavController,
    qualificationViewModel: QualificationViewModel,
    userViewModel: UserViewModel,
    userRoleViewModel: UserRoleViewModel,
    studentViewModel: StudentViewModel,
    paddingValues: PaddingValues
) {


    var experience by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TitleSection(stringResource(id = R.string.preparing_text))

        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.8f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {


                RegisterTextField(value = experience, onValueChange = { experience = it }, label = stringResource(id = R.string.experience_field), false, 94.dp)


            }


        }

        studentViewModel.updateRating(0.0)

        val isFormValid = experience.isNotBlank()

        CustomButton(
            text = stringResource(id = R.string.next_button),
            onClick = {
                studentViewModel.updateExperience(experience)
                navController.navigate(NavRoutes.RegisterStudentThird.ROUTE)
            },
            modifier = Modifier.width(350.dp),
            enabled = isFormValid
        )
    }
}

