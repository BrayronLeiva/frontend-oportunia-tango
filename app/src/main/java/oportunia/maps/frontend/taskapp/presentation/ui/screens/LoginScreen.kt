package oportunia.maps.frontend.taskapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import androidx.compose.ui.res.stringResource
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.SubtitleSection
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.data.datasource.userrole.UserRoleProvider
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.theme.Black
import oportunia.maps.frontend.taskapp.presentation.ui.theme.lightBlue
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    userRoleViewModel: UserRoleViewModel,
    paddingValues: PaddingValues
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // Observamos el estado de userRole desde el ViewModel
    val userRoleState by userRoleViewModel.userRole.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(0.85f)
        ) {
            Image(
                painter = painterResource(id = R.drawable.oportunia_maps),
                contentDescription = "OportunIA Maps Logo",
                modifier = Modifier.size(180.dp),
                contentScale = ContentScale.Fit
            )

            TitleSection(stringResource(id = R.string.title_welcome))

            SubtitleSection(
                stringResource(id = R.string.main_login_subtitle),
                stringResource(id = R.string.main_register_subtitle_sub)
            )

            RegisterTextField(
                value = email,
                onValueChange = { email = it },
                label = stringResource(id = R.string.email_field)
            )

            RegisterTextField(
                value = password,
                onValueChange = { password = it },
                label = stringResource(id = R.string.password_field),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                visualTransformation = PasswordVisualTransformation()
            )

            RegisterText(navController)

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage)
            }

            // Verificamos el estado de carga y el error
            when (userRoleState) {
                is UserRoleState.Loading -> {
                    CircularProgressIndicator() // Muestra un spinner mientras se hace la operación
                }
                is UserRoleState.Success -> {
                    val loggedInUser = (userRoleState as UserRoleState.Success).userRole
                    // Si el login es exitoso, navegamos a la pantalla correspondiente
                    LaunchedEffect(loggedInUser) {
                        val userRole = loggedInUser.role.name
                        when (userRole) {
                            TypeUser.STU -> {
                                navController.navigate(NavRoutes.StudentMap.ROUTE)
                            }
                            TypeUser.COM -> {
                                navController.navigate(NavRoutes.CompanyMap.ROUTE)
                            }
                        }
                    }
                }
                is UserRoleState.Failure -> {
                    Text("Invalid email or password.", color = Color.Red) // Mensaje de error si no se encuentra al usuario
                }
                is UserRoleState.Error -> {
                    val errorMessage = (userRoleState as UserRoleState.Error).message
                    Text(errorMessage, color = Color.Red) // Muestra el mensaje de error
                }
                is UserRoleState.Empty -> {
                    //Text("Invalid email or password.", color = Color.Red) // Mensaje de error si no se encuentra al usuario
                }
            }

            CustomButton(
                text = stringResource(id = R.string.main_login),
                onClick = {
                    // Inicia el login llamando al método del ViewModel
                    userRoleViewModel.loginUser(email, password)
                }
            )
        }
    }
}

@Composable
fun RegisterText(navController: NavController) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(id = R.string.no_account) + " ")
        addStyle(
            style = SpanStyle(color = lightBlue),
            start = length,
            end = length + stringResource(id = R.string.register_here).length
        )
        addStringAnnotation(
            tag = "REGISTER",
            annotation = "register",
            start = length,
            end = length + stringResource(id = R.string.register_here).length
        )
        append(stringResource(id = R.string.register_here))
    }

    Text(
        text = annotatedString,
        modifier = Modifier
            .padding(top = 8.dp)
            .clickable {
                val registerAnnotation = annotatedString.getStringAnnotations(
                    tag = "REGISTER", start = 0, end = annotatedString.length
                ).firstOrNull()
                if (registerAnnotation != null) {
                    navController.navigate(NavRoutes.MainRegister.ROUTE)
                }
            },
        style = androidx.compose.ui.text.TextStyle(color = Black)
    )
}