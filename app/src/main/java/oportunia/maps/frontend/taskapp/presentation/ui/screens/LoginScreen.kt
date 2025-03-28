package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.withStyle
import oportunia.maps.frontend.taskapp.data.datasource.model.enumClasses.TypeUser
import oportunia.maps.frontend.taskapp.data.datasource.userrole.UserRoleProvider
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.presentation.navigation.NavRoutes
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.NextButtom
import oportunia.maps.frontend.taskapp.presentation.ui.theme.Black
import oportunia.maps.frontend.taskapp.presentation.ui.theme.lightBlue

@Composable
fun LoginScreen(
    navController: NavController,
    paddingValues: PaddingValues
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

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
                    /*navController.navigate(TODO: NavRoutes.Register.ROUTE)*/
                }
            },
        style = androidx.compose.ui.text.TextStyle(color = Black)
    )
}