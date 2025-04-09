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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.NextButtom
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.SelectionTagInput
import oportunia.maps.frontend.taskapp.presentation.ui.components.SubtitleSection
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel



@Composable
fun RegisterStudentSecond(
    navController: NavController,
    qualificationViewModel: QualificationViewModel,
    paddingValues: PaddingValues
) {


    // Obtener la lista de habilidades directamente del StateFlow
    val qualifications = qualificationViewModel.qualificationList.collectAsState().value
    val habilidades = qualifications.map { it.name }
    // Llamamos al método para cargar las qualifications
    LaunchedEffect(Unit) {
        qualificationViewModel.findAllQualifications()
    }

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

                RegisterTextField(value = interests, onValueChange = { interests = it },
                    stringResource(id = R.string.interests_field), false, 124.dp)

                Spacer(modifier = Modifier.height(42.dp))


                SelectionTagInput(habilidades)

            }


        }
        CustomButton(stringResource(id = R.string.next_button), onClick = {navController.navigate(
            NavRoutes.Home.ROUTE)}, modifier = Modifier.width(350.dp), 350.dp)

    }
}
