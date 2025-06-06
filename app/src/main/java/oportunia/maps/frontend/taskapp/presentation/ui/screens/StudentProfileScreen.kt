package oportunia.maps.frontend.taskapp.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import oportunia.maps.frontend.taskapp.R
import oportunia.maps.frontend.taskapp.presentation.ui.components.CustomButton
import oportunia.maps.frontend.taskapp.presentation.ui.components.ProfileInfoCard
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentState
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel

@Composable
fun StudentProfileScreen(
    navController: NavController,
    studentViewModel: StudentViewModel,
    userId: Long,
    onLogOut: () -> Unit
) {

    LaunchedEffect(Unit) {
        studentViewModel.getLoggedStudent()
    }

    val selectedStudent by studentViewModel.selectedStudent.collectAsState()
    val studentState by studentViewModel.studentState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        // Handle the different internship states
        when (val state = studentState) {
            is StudentState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is StudentState.Empty -> {
                Text(
                    text = "No stundent information available.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            is StudentState.Success -> {
                Spacer(modifier = Modifier.height(24.dp))

                // Profile Image
                Image(
                    painter = painterResource(id = R.drawable.oportunia_maps), // Replace with your drawable
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Student Name
                selectedStudent?.let {
                    Text(
                        text = it.name,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Student Rating
                Text(
                    text = "⭐ ${selectedStudent?.rating}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFA500) // Orange for rating
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Info Card
                ProfileInfoCard(title = "Identification", value = selectedStudent?.identification.toString())
                selectedStudent?.let { ProfileInfoCard(title = "Personal Info", value = it.personalInfo) }
                selectedStudent?.let { ProfileInfoCard(title = "Experience", value = it.experience) }

                Spacer(modifier = Modifier.height(32.dp))

                // Logout Button
                CustomButton(
                    onClick = {
                        onLogOut()
                    },
                    text = "Logout",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                )
            }
            is StudentState.Error -> {
                Text(
                    text = "Error: ${state.message}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

    }
}

