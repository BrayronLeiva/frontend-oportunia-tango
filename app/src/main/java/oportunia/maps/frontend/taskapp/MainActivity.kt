package oportunia.maps.frontend.taskapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import oportunia.maps.frontend.taskapp.presentation.navigation.NavGraph
import oportunia.maps.frontend.taskapp.presentation.ui.components.BottomNavigationBarStudent
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel

/**
 * Main activity that serves as the entry point for the application.
 * Initializes the TaskViewModel and sets up the Compose UI with the main screen.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val locationCompanyViewModel: LocationCompanyViewModel by viewModels()
    private val qualificationViewModel: QualificationViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    private val internshipLocationViewModel: InternshipLocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getIntExtra("userId", -1).toLong()  // -1 es el valor por defecto

        Log.d("MainActivity", "User ID recibido: $userId")
        setContent {
            TaskAppTheme {
                MainScreen(
                    qualificationViewModel,
                    internshipLocationViewModel,
                    locationCompanyViewModel,
                    studentViewModel,
                    userId
                )
            }
        }
    }
}


/**
 * Preview composable for the MainScreen.
 * Displays the application UI with the main navigation structure.
 */
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TaskAppTheme {
        // Create a minimal MainScreen preview that doesn't require a real ViewModel
        PreviewMainScreen()
    }
}

/**
 * A simplified version of MainScreen for preview purposes only.
 * Uses static content instead of requiring a real ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PreviewMainScreen() {

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        // Display placeholder content instead of the actual NavGraph
        // which requires a real ViewModel
        Text(
            text = "Preview: Navigation Content",
            modifier = Modifier.padding(paddingValues)
        )
    }
}

@Composable
fun MainScreen(
    qualificationViewModel: QualificationViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    locationCompanyViewModel: LocationCompanyViewModel,
    studentViewModel: StudentViewModel,
    userId: Long
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        locationCompanyViewModel.findAllLocations()
    }

    // Mantener el estado de la ruta actual
    var currentDestination by remember { mutableStateOf<String?>(null) }

    // Monitorear cambios en la ruta de navegación
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            currentDestination = entry.destination.route
        }
    }


    Scaffold (
        bottomBar = {
            if (
                currentDestination != null &&

                currentDestination != "mainRegister"
                &&
                currentDestination != "registerStudentFirst"
                &&
                currentDestination != "registerStudentSecond"
                &&
                currentDestination != "home"
                &&
                currentDestination != "companyMap"
                &&
                !currentDestination!!.startsWith("internshipCompany/")
            ) {
                BottomNavigationBarStudent(navController = navController)
            }
        }

    ) { paddingValues ->
        NavGraph(
            navController = navController,
            locationCompanyViewModel = locationCompanyViewModel,
            internshipLocationViewModel = internshipLocationViewModel,
            qualificationViewModel = qualificationViewModel,
            studentViewModel = studentViewModel,
            paddingValues = paddingValues,
            userId = userId
        )
    }
}