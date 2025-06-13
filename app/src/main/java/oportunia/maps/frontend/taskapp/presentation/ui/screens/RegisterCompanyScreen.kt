package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.data.remote.dto.RegisterCompanyCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.InternshipType
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.uriToFile
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RegisterState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RegisterViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel

@Composable
fun RegisterCompanyScreen(
    userRoleViewModel: UserRoleViewModel,
    userViewModel: UserViewModel,
    registerViewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    val userDraft by userViewModel.userDraft.collectAsState()
    val email = userDraft.email
    val password = userDraft.password

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var history by remember { mutableStateOf("") }
    var mision by remember { mutableStateOf("") }
    var vision by remember { mutableStateOf("") }
    var corporateCulture by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var internshipType by remember { mutableStateOf<InternshipType?>(null) }

    var imageProfile by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var hasSubmitted by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val companyState by registerViewModel.registerCompanyState.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            imageProfile = uri?.toString() ?: ""
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Image Picker
        Text(stringResource(R.string.select_profile_image))
        Button(onClick = { launcher.launch("image/*") }) {
            Text(stringResource(R.string.select_image))
        }
        imageUri?.let {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(it)
                    .crossfade(true)
                    .error(R.drawable.default_profile_icon)
                    .fallback(R.drawable.default_profile_icon)
                    .build(),
                contentDescription = stringResource(R.string.profile_picture_content_description),
                modifier = Modifier.size(160.dp).clip(CircleShape)
            )
        }

        // Representative Info
        Text(stringResource(R.string.representative_info), style = MaterialTheme.typography.titleMedium)
        RegisterTextField(value = firstName, onValueChange = { firstName = it }, label = stringResource(R.string.label_first_name))
        RegisterTextField(value = lastName, onValueChange = { lastName = it }, label = stringResource(R.string.label_last_name))

        // Company Info
        Text(stringResource(R.string.company_info), style = MaterialTheme.typography.titleMedium)
        RegisterTextField(value = name, onValueChange = { name = it }, label = stringResource(R.string.label_company_name))
        RegisterTextField(value = description, onValueChange = { description = it }, label = stringResource(R.string.label_description))
        RegisterTextField(value = history, onValueChange = { history = it }, label = stringResource(R.string.label_history))
        RegisterTextField(value = mision, onValueChange = { mision = it }, label = stringResource(R.string.label_mission))
        RegisterTextField(value = vision, onValueChange = { vision = it }, label = stringResource(R.string.label_vision))
        RegisterTextField(value = corporateCulture, onValueChange = { corporateCulture = it }, label = stringResource(R.string.label_corporate_culture))
        RegisterTextField(
            value = contact,
            onValueChange = { contact = it.filter { c -> c.isDigit() } },
            label = stringResource(R.string.label_contact),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Internship Type
        Text(stringResource(R.string.internship_type_title))
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            listOf(
                stringResource(R.string.internship_type_inp) to InternshipType.INP,
                stringResource(R.string.internship_type_mix) to InternshipType.MIX,
                stringResource(R.string.internship_type_rem) to InternshipType.REM
            ).forEach { (label, type) ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = internshipType == type,
                        onClick = { internshipType = type }
                    )
                    Text(label)
                }
            }
        }

        // Submit Button
        val isFormValid = listOf(
            firstName, lastName, email, password, name, description, history,
            mision, vision, corporateCulture, contact
        ).all { it.isNotBlank() } && internshipType != null

        CustomButton(
            text = stringResource(R.string.register_company_button),
            onClick = {
                val dto = RegisterCompanyCreateDto(
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    password = password,
                    enabled = true,
                    tokenExpired = false,
                    nameCompany = name,
                    description = description,
                    history = history,
                    mision = mision,
                    vision = vision,
                    corporateCultur = corporateCulture,
                    contactCompany = contact.toInt(),
                    ratingCompany = 0.0,
                    internshipType = internshipType!!.name,
                    imageProfile = imageProfile
                )
                registerViewModel.registerCompany(dto)
                hasSubmitted = true
            },
            enabled = isFormValid,
            modifier = Modifier.fillMaxWidth()
        )
    }

    // Handle result
    LaunchedEffect(companyState) {
        if (hasSubmitted) {
            when (val state = companyState) {
                is RegisterState.Success -> {
                    val registerCompanyDto = state.data
                    val company = registerCompanyDto.company
                    val user = registerCompanyDto.user

                    val userId = user.id
                    userRoleViewModel.saveUserRoleCompany(userId)

                    if (imageUri != null) {
                        val file = withContext(Dispatchers.IO) {
                            uriToFile(imageUri!!, context)
                        }
                        if (file != null) {
                            registerViewModel.uploadCompanyImage(company.idCompany, file)
                        }
                    }

                    onRegisterSuccess()
                }

                is RegisterState.Error -> {
                    Log.e("RegisterCompanyScreen", "Error: ${state.message}")
                }

                else -> Unit
            }
        }
    }
}