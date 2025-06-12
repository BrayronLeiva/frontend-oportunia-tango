package oportunia.maps.frontend.taskapp.presentation.ui.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import oportunia.maps.frontend.taskapp.presentation.ui.components.RegisterTextField
import androidx.compose.ui.res.stringResource
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.SubtitleSection
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.LanguageSelector
import oportunia.maps.frontend.taskapp.presentation.ui.theme.Black
import oportunia.maps.frontend.taskapp.presentation.ui.theme.lightBlue
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserState

@Composable
fun LoginScreen(
    userViewModel: UserViewModel,
    paddingValues: PaddingValues,
    onLoginSuccess: (Int) -> Unit,
    onRegisterClick: (Context) -> Unit
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val userState by userViewModel.userState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            LanguageSelector()
        }
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

            RegisterText(onRegisterClick = { onRegisterClick(context) })

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = Color.Red)
            }

            when (userState) {
                is UserState.Loading -> {
                    CircularProgressIndicator()
                }
                is UserState.Success -> {
                    val loggedInUser = (userState as UserState.Success).user
                    LaunchedEffect(loggedInUser.id) {
                        onLoginSuccess(loggedInUser.id.toInt())
                    }
                }
                is UserState.Failure -> {
                    Text(stringResource(id = R.string.invalid_login), color = Color.Red)
                }
                is UserState.Error -> {
                    val message = (userState as UserState.Error).message
                    Text(message, color = Color.Red)
                }
                is UserState.Empty -> {
                    // Optional: Show nothing or a placeholder
                }
            }

            CustomButton(
                text = stringResource(id = R.string.main_login),
                onClick = {
                    userViewModel.loginUser(email, password)
                }
            )
        }
    }
}

@Composable
fun RegisterText(onRegisterClick: () -> Unit) {
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
            .clickable(onClick = onRegisterClick),
        style = androidx.compose.ui.text.TextStyle(color = Black)
    )
}