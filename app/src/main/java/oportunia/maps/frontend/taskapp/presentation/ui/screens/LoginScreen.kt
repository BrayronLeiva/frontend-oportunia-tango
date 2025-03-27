package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.SubtitleSection
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.data.datasource.userrole.UserRoleProvider
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.NextButton

@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TitleSection(stringResource(id = R.string.title_welcome))

        SubtitleSection(
            stringResource(id = R.string.main_login_subtitle),
            stringResource(id = R.string.main_register_subtitle_sub)
        )

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

                if (errorMessage.isNotEmpty()) {
                    Text(errorMessage)
                }
            }
        }

        CustomButton(
            text = stringResource(id = R.string.main_login),
            onClick = {
                if (UserRoleProvider.verifyUserCredentials(email, password)) {
                    val userRole = UserRoleProvider.getUserRoleByEmail(email)
                    when (userRole) {
                        TypeUser.STU -> {
                            navController.navigate(NavRoutes.StudentMap.ROUTE)
                        }
                        TypeUser.COM -> {
                            navController.navigate(NavRoutes.CompanyMap.ROUTE)
                        }
                        null -> {
                            errorMessage = "User role not found."
                        }
                    }
                } else {
                    errorMessage = "Invalid email or password."
                }
            }
        )
    }
}