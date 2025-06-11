package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.SelectionTagInput
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserCreateState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes

@Composable
fun RegisterStudentThird(
    navController: NavController,
    qualificationViewModel: QualificationViewModel,
    userViewModel: UserViewModel,
    userRoleViewModel: UserRoleViewModel,
    studentViewModel: StudentViewModel,
    paddingValues: PaddingValues
) {

    val costaRica = LatLng(9.7489, -83.7534)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(costaRica, 8f)
    }

    var markerPosition by remember { mutableStateOf<LatLng?>(null) }


    val userDraft = userViewModel.userDraft.collectAsState().value
    val studentDraft = studentViewModel.studentDraft.collectAsState().value


    val studentState by studentViewModel.studentState.collectAsState()
    val userCreateState by userViewModel.userCreateState.collectAsState()
    val userRoleState by userRoleViewModel.userRoleState.collectAsState()
    val isProcessing by studentViewModel.isProcessing.collectAsState()

    LaunchedEffect(Unit) {
        qualificationViewModel.findAllQualifications()
    }

    // Lógica de reacción al estado del usuario
    LaunchedEffect(userCreateState) {
        if (userCreateState is UserCreateState.Success) {
            val userid = (userCreateState as UserCreateState.Success).user.id
            Log.e("Saving", (userid.toString()))
            userRoleViewModel.saveUserRoleStudent(userid)
        }
    }

    LaunchedEffect(userRoleState) {
        if (userRoleState is UserRoleState.Success) {
            val userid = (userCreateState as UserCreateState.Success).user.id
            Log.e("Saving", (userid.toString()))
            studentViewModel.updateUser(userid)

            Log.e("Saving", studentDraft.toString())
            studentViewModel.saveStudent()
        }
        studentViewModel.stopRegistrationFlow()
    }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TitleSection(stringResource(id = R.string.preparing_text))

        Box(modifier = Modifier
            .weight(1f)
            .fillMaxWidth()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                onMapClick = { latLng ->
                    markerPosition = latLng
                    studentViewModel.updateLatitude(latLng.latitude)
                    studentViewModel.updateLongitude(latLng.longitude)
                    Log.e("Selectec", "$latLng.latitude  $latLng.longitude")
                }
            ) {
                markerPosition?.let {
                    Marker(
                        state = MarkerState(position = it),
                        title = "Ubicación seleccionada"
                    )
                }
            }

            Text(
                text = markerPosition?.let { "Lat: ${it.latitude}, Lng: ${it.longitude}" } ?: "Toca el mapa para seleccionar ubicación",
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(16.dp)
                    .background(Color.White)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            )


        }

        studentViewModel.updateRating(0.0)

        if (isProcessing) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            when (studentState) {
                is StudentState.Success -> {
                    navController.navigate(NavRoutes.RegisterStudentFinal.ROUTE)
                }
                is StudentState.Error -> {
                    val errorMessage = (studentState as StudentState.Error).message
                    Text(text = errorMessage, color = Color.Red)
                }

                else -> {
                    CustomButton(
                        text = stringResource(id = R.string.next_button),
                        onClick = {
                            Log.e("Saving", userDraft.toString())
                            studentViewModel.startRegistrationFlow()
                            userViewModel.saveUser2()
                        },
                        modifier = Modifier.width(350.dp),
                        enabled = markerPosition != null
                    )
                }
            }
        }
    }
}
