package oportunia.maps.frontend.taskapp.presentation.ui.screens


import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.SelectionTagInput
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestCreateState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentImageState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserCreateState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel
import java.io.File


@Composable
fun RegisterStudentSecond(
    navController: NavController,
    qualificationViewModel: QualificationViewModel,
    userViewModel: UserViewModel,
    userRoleViewModel: UserRoleViewModel,
    studentViewModel: StudentViewModel,
    paddingValues: PaddingValues,
    onRegisterSuccess: (Int) -> Unit
) {


    // Obtener la lista de habilidades directamente del StateFlow
    val qualifications = qualificationViewModel.qualificationList.collectAsState().value
    val userDraft = userViewModel.userDraft.collectAsState().value
    val studentDraft = studentViewModel.studentDraft.collectAsState().value

    //val studentCreated = studentViewModel.registeredStudent.collectAsState().value

    val studentState by studentViewModel.studentState.collectAsState()
    val userCreateState by userViewModel.userCreateState.collectAsState()
    val userRoleState by userRoleViewModel.userRoleState.collectAsState()

    val habilidades = qualifications.map { it.name }
    // Llamamos al método para cargar las qualifications
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
    }

    var interests by remember { mutableStateOf("") }
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

                //RegisterTextField(value = interests, onValueChange = { interests = it },
                    //stringResource(id = R.string.interests_field), false, 124.dp)

                //Spacer(modifier = Modifier.height(42.dp))
                RegisterTextField(value = experience, onValueChange = { experience = it }, label = stringResource(id = R.string.experience_field), false, 94.dp)

                SelectionTagInput(habilidades)

            }


        }

        studentViewModel.updateRating(0.0)
        // Observamos el estado del estudiante
        if (studentState is StudentState.Loading || userCreateState is UserCreateState.Loading || userRoleState is UserRoleState.Loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            when (studentState) {

                is StudentState.Success -> {
                    ImageUploader((studentState as StudentState.Success).student.id, studentViewModel)

                    //val loggedInStudent = (studentState as StudentState.Success).student
                    //LaunchedEffect(loggedInStudent.id) {
                        //onRegisterSuccess(loggedInStudent.id.toInt())
                    //}
                }

                is StudentState.Error -> {
                    val errorMessage = (studentState as StudentState.Error).message
                    Text(text = errorMessage, color = Color.Red)
                }

                else -> {
                    val isFormValid = experience.isNotBlank()

                    CustomButton(
                        text = stringResource(id = R.string.next_button),
                        onClick = {
                            studentViewModel.updateExperience(experience)
                            Log.e("Saving", userDraft.toString())
                            userViewModel.saveUser2()
                            //studentViewModel.saveStudent()
                        },
                        modifier = Modifier.width(350.dp),
                        enabled = isFormValid
                    )
                }
            }
        }
    }
}


@Composable
fun ImageUploader(studentId: Long, viewModel: StudentViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            val file = uriToFile(uri, context)
            if (file != null) {
                viewModel.uploadImage(studentId, file)
            }
        }
    }

    Button(onClick = { launcher.launch("image/*") }) {
        Text("Cargar imagen")
    }

    when (val state = viewModel.studentImageState.collectAsState().value) {
        is StudentImageState.ImageUploadSuccess -> {
            Text("Imagen subida con éxito")
            AsyncImage(model = state.imageUrl, contentDescription = null)
        }
        is StudentImageState.Error -> Text("Error: ${state.message}", color = Color.Red)
        StudentImageState.Loading -> CircularProgressIndicator()
        else -> {}
    }
}

fun uriToFile(uri: Uri, context: Context): File? {
    val inputStream = context.contentResolver.openInputStream(uri) ?: return null
    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    tempFile.outputStream().use { fileOut ->
        inputStream.copyTo(fileOut)
    }
    return tempFile
}


