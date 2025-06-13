package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterLineTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel


@Composable
fun RegisterStudentFirst(
    navController: NavController,
    userviewModel: UserViewModel,
    studentViewModel: StudentViewModel,
    paddingValues: PaddingValues
) {
    var name by remember { mutableStateOf("") }
    var idCard by remember { mutableStateOf("") }
    var personalInfo by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
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
                RegisterLineTextField(value = name, onValueChange = { name = it }, label = stringResource(id = R.string.name_field), true, 56.dp)
                RegisterLineTextField(value = lastName, onValueChange = { lastName = it }, label = stringResource(id = R.string.last_name_field), true, 56.dp)
                RegisterLineTextField(value = idCard, onValueChange = { idCard = it }, label = stringResource(id = R.string.id_field), true, 56.dp)
                RegisterTextField(value = personalInfo, onValueChange = { personalInfo = it }, stringResource(id = R.string.personal_info_field), false, 94.dp)

            }

        }

        val isFormValid = name.isNotBlank() &&
                lastName.isNotBlank() &&
                idCard.isNotBlank() &&
                personalInfo.isNotBlank()

        CustomButton(
            text = stringResource(id = R.string.next_button),
            onClick = {
                userviewModel.updateFirstName(name)
                userviewModel.updateLastName(lastName)

                studentViewModel.updateName(name)
                studentViewModel.updateIdentification(idCard)
                studentViewModel.updatePersonalInfo(personalInfo)
                navController.navigate(NavRoutes.RegisterStudentSecond.ROUTE)
            },
            modifier = Modifier.width(350.dp),
            enabled = isFormValid
        )


    }
}
