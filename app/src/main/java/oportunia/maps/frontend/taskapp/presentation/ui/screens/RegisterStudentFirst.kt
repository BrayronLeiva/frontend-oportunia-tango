package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.NextButtom
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterLineTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.SubtitleSection
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel


@Composable
fun RegisterStudentFirst(
    navController: NavController,
    paddingValues: PaddingValues
) {
    var name by remember { mutableStateOf("") }
    var idCard by remember { mutableStateOf("") }
    var personalInfo by remember { mutableStateOf("") }
    var experience by remember { mutableStateOf("") }
    var interests by remember { mutableStateOf("") }

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
                RegisterLineTextField(value = name, onValueChange = { name = it }, label = stringResource(id = R.string.name_field), true, 56.dp)
                RegisterLineTextField(value = idCard, onValueChange = { idCard = it }, label = stringResource(id = R.string.id_field), true, 56.dp)
                RegisterTextField(value = personalInfo, onValueChange = { personalInfo = it }, stringResource(id = R.string.personal_info_field), false, 94.dp)
                RegisterTextField(value = experience, onValueChange = { experience = it }, label = stringResource(id = R.string.experience_field), false, 94.dp)
            }

        }
        CustomButton(stringResource(id = R.string.next_button), onClick = {navController.navigate(NavRoutes.RegisterStudentSecond.ROUTE)}, modifier = Modifier.width(350.dp), 350.dp)

    }
}
