package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.InternshipType
import oportunia.maps.frontend.taskapp.domain.model.Company
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.ImageUploader
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import oportunia.maps.frontend.taskapp.presentation.ui.components.uriToFile
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel

@Composable
fun RegisterCompanyScreen(
    userViewModel: UserViewModel,
    companyViewModel: CompanyViewModel,
    userRoleViewModel: UserRoleViewModel,
    onRegisterSuccess: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var history by remember { mutableStateOf("") }
    var mision by remember { mutableStateOf("") }
    var vision by remember { mutableStateOf("") }
    var corporateCulture by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }
    var internshipType by remember { mutableStateOf<InternshipType?>(null) }
    var imageProfile by remember { mutableStateOf("") }

    val companyState by companyViewModel.companyState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val context = LocalContext.current
        var imageUri by remember { mutableStateOf<Uri?>(null) }

        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            imageProfile = uri?.toString() ?: ""
        }

        Text("Seleccione una imagen de perfil:")
        Button(onClick = { launcher.launch("image/*") }) {
            Text("Seleccionar imagen")
        }
        if (imageUri != null) {
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(imageUri)
                    .crossfade(true)
                    .error(R.drawable.default_profile_icon)
                    .fallback(R.drawable.default_profile_icon)
                    .build(),
                contentDescription = stringResource(R.string.profile_picture_content_description),
                modifier = Modifier
                    .size(160.dp)
                    .clip(CircleShape)
            )
        }

        // Section: Representante
        Text("Información del Representante", style = MaterialTheme.typography.titleMedium)
        RegisterTextField(value = firstName, onValueChange = { firstName = it }, label = "Nombre del representante")
        RegisterTextField(value = lastName, onValueChange = { lastName = it }, label = "Apellido del representante")

        // Section: Compañía
        Text("Información de la Compañía", style = MaterialTheme.typography.titleMedium)
        RegisterTextField(value = name, onValueChange = { name = it }, label = "Nombre de la empresa")
        RegisterTextField(value = description, onValueChange = { description = it }, label = "Descripción")
        RegisterTextField(value = history, onValueChange = { history = it }, label = "Historia")
        RegisterTextField(value = mision, onValueChange = { mision = it }, label = "Misión")
        RegisterTextField(value = vision, onValueChange = { vision = it }, label = "Visión")
        RegisterTextField(value = corporateCulture, onValueChange = { corporateCulture = it }, label = "Cultura corporativa")
        RegisterTextField(
            value = contact,
            onValueChange = { contact = it.filter { c -> c.isDigit() } },
            label = "Contacto",
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Internship type
        Text("Modalidad de pasantía:")
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf(
                "Presencial" to InternshipType.INP,
                "Mixta" to InternshipType.MIX,
                "Remota" to InternshipType.REM
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

        val isFormValid = firstName.isNotBlank() &&
                lastName.isNotBlank() &&
                name.isNotBlank() &&
                description.isNotBlank() &&
                history.isNotBlank() &&
                mision.isNotBlank() &&
                vision.isNotBlank() &&
                corporateCulture.isNotBlank() &&
                contact.isNotBlank() &&
                internshipType != null

        val savedUser by userViewModel.savedUser.collectAsState()
        var companyRegistered by remember { mutableStateOf(false) }

        CustomButton(
            text = "Registrar Empresa",
            onClick = {
                userViewModel.updateFirstName(firstName)
                userViewModel.updateLastName(lastName)
                userViewModel.saveUser2()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = isFormValid
        )

        LaunchedEffect(savedUser) {
            if (savedUser != null && !companyRegistered) {
                val company = Company(
                    name = name,
                    description = description,
                    history = history,
                    mision = mision,
                    vision = vision,
                    corporateCultur = corporateCulture,
                    contact = contact.toInt(),
                    rating = 0.0,
                    internshipType = internshipType!!,
                    imageProfile = imageProfile,
                    user = savedUser!!
                )
                userRoleViewModel.saveUserRoleCompany(savedUser!!.id)
                companyViewModel.registerCompany(company)
                companyRegistered = true
            }
        }

        var imageProfileUploaded by remember { mutableStateOf(false) }

        LaunchedEffect(companyState) {
            when (val state = companyState) {
                is CompanyState.Success -> {
                    if (!imageProfileUploaded && imageUri != null) {
                        val file = withContext(Dispatchers.IO) {
                            uriToFile(imageUri!!, context)
                        }
                        if (file != null) {
                            companyViewModel.uploadImage(state.company.id!!, file)
                            imageProfileUploaded = true
                            onRegisterSuccess()
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}