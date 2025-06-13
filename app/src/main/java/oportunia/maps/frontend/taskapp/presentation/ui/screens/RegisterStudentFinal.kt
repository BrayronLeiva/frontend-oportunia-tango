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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.ImageUploader
import oportunia.maps.frontend.taskapp.presentation.ui.components.TitleSection
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RegisterState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RegisterViewModel

@Composable
fun RegisterStudentFinal(
    registerViewModel: RegisterViewModel,
    paddingValues: PaddingValues,
    onRegisterSuccess: () -> Unit
) {
    val studentRegisterState by registerViewModel.registerStudentState.collectAsState()
    val studentImageUploadState by registerViewModel.studentImageUploadState.collectAsState()

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
                when (val imageState = studentImageUploadState) {
                    is RegisterState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is RegisterState.Success -> {
                        Text(
                            stringResource(id = R.string.student_image_upload_success),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(8.dp)
                        )

                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageState.data)
                                .crossfade(true)
                                .error(R.drawable.default_profile_icon)
                                .fallback(R.drawable.default_profile_icon)
                                .build(),
                            contentDescription = stringResource(R.string.profile_picture_content_description),
                            modifier = Modifier
                                .size(180.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        CustomButton(
                            text = stringResource(id = R.string.register_button),
                            onClick = onRegisterSuccess,
                            modifier = Modifier
                                .fillMaxWidth(0.85f)
                                .height(56.dp)
                        )
                    }

                    is RegisterState.Error -> {
                        Text(
                            text = stringResource(id = R.string.error_message, imageState.message),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    else -> {
                        // Upload the image only if registration was successful
                        when (val registerState = studentRegisterState) {
                            is RegisterState.Success -> {
                                val studentId = registerState.data.student.id
                                ImageUploader(studentId, registerViewModel)
                            }

                            is RegisterState.Loading -> {
                                CircularProgressIndicator()
                            }

                            is RegisterState.Error -> {
                                Text(
                                    text = stringResource(id = R.string.error_message, registerState.message),
                                    color = Color.Red,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            else -> {
                                Text("Awaiting student registration...")
                            }
                        }
                    }
                }
            }
        }
    }
}

