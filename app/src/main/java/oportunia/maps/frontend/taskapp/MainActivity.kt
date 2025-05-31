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
import oportunia.maps.frontend.taskapp.data.datasource.qualification.QualificationDataSourceImpl
import oportunia.maps.frontend.taskapp.data.datasource.task.TaskDataSourceImpl
import oportunia.maps.frontend.taskapp.data.datasource.locationcompany.LocationCompanyDataSourceImpl
import oportunia.maps.frontend.taskapp.data.mapper.CompanyMapper
import oportunia.maps.frontend.taskapp.data.mapper.InternshipLocationMapper
import oportunia.maps.frontend.taskapp.data.mapper.InternshipMapper
import oportunia.maps.frontend.taskapp.data.mapper.LocationCompanyMapper
import oportunia.maps.frontend.taskapp.data.mapper.PriorityMapper
import oportunia.maps.frontend.taskapp.data.mapper.QualificationMapper
import oportunia.maps.frontend.taskapp.data.mapper.StatusMapper
import oportunia.maps.frontend.taskapp.data.mapper.TaskMapper
import oportunia.maps.frontend.taskapp.data.mapper.UserMapper
import oportunia.maps.frontend.taskapp.data.repository.InternshipLocationRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.LocationCompanyRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.QualificationRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.TaskRepositoryImpl
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import oportunia.maps.frontend.taskapp.presentation.factory.InternshipLocationViewModelFactory
import oportunia.maps.frontend.taskapp.presentation.factory.LocationCompanyViewModelFactory
import oportunia.maps.frontend.taskapp.presentation.factory.QualificationViewModelFactory
import oportunia.maps.frontend.taskapp.presentation.factory.TaskViewModelFactory
import oportunia.maps.frontend.taskapp.presentation.navigation.NavGraph
import oportunia.maps.frontend.taskapp.presentation.ui.components.BottomNavigationRow
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel

/**
 * Main activity that serves as the entry point for the application.
 * Initializes the TaskViewModel and sets up the Compose UI with the main screen.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val taskViewModel: TaskViewModel by viewModels {
        // Create mappers
        val priorityMapper = PriorityMapper()
        val statusMapper = StatusMapper()
        val taskMapper = TaskMapper(priorityMapper, statusMapper)
        // Create data source with mapper
        val dataSource = TaskDataSourceImpl(taskMapper)

        // Create repository with data source and mapper
        val taskRepository = TaskRepositoryImpl(dataSource, taskMapper)

        TaskViewModelFactory(taskRepository)
    }

    /*private val locationCompanyRepository: LocationCompanyRepository by lazy {
        val userMapper = UserMapper()
        val companyMapper = CompanyMapper(userMapper)
        val locationCompanyMapper = LocationCompanyMapper(companyMapper)

        val locationCompanyDataSource = LocationCompanyDataSourceImpl(locationCompanyMapper)

        LocationCompanyRepositoryImpl(locationCompanyDataSource, locationCompanyMapper)
    }

    private val qualificationViewModel: QualificationViewModel by viewModels {

        val qualificationMapper = QualificationMapper(null)

        val dataQualificationSource = QualificationDataSourceImpl(qualificationMapper)

        val qualificationRepository = QualificationRepositoryImpl(dataQualificationSource, qualificationMapper)

        QualificationViewModelFactory(qualificationRepository)
    }
    */

    private val locationCompanyViewModel: LocationCompanyViewModel by viewModels()

    private val qualificationViewModel: QualificationViewModel by viewModels()

    private val studentViewModel: StudentViewModel by viewModels()

    //private val userRoleViewModel: UserRoleViewModel by viewModels()

    //private val internshipLocationViewModel: InternshipLocationViewModel by viewModels()

    /*private val internshipLocationViewModelFactory: InternshipLocationViewModel by viewModels {
        val userMapper = UserMapper()
        val internshipMapper = InternshipMapper()
        val companyMapper = CompanyMapper(userMapper)
        val locationCompanyMapper = LocationCompanyMapper(companyMapper)
        val internshipLocationMapper = InternshipLocationMapper(locationCompanyMapper, internshipMapper)

        val internshipLocationDataSource = InternshipLocationDataSourceImpl(internshipLocationMapper, internshipMapper)

        val internshipLocationRepository = InternshipLocationRepositoryImpl(internshipLocationDataSource, internshipLocationMapper, internshipMapper)

        InternshipLocationViewModelFactory(locationCompanyRepository, internshipLocationRepository)
    }*/




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = intent.getIntExtra("userId", -1).toLong()  // -1 es el valor por defecto

        Log.d("MainActivity", "User ID recibido: $userId")
        setContent {
            TaskAppTheme {
                MainScreen(
                    taskViewModel,
                    qualificationViewModel,
                    locationCompanyViewModel,
                    studentViewModel,
                    userId
                    //internshipLocationViewModel
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
    taskViewModel: TaskViewModel,
    qualificationViewModel: QualificationViewModel,
    locationCompanyViewModel: LocationCompanyViewModel,
    studentViewModel: StudentViewModel,
    userId: Long
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        taskViewModel.findAllTasks()
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
                BottomNavigationRow(navController = navController)
            }
        }

    ) { paddingValues ->
        NavGraph(
            navController = navController,
            locationCompanyViewModel = locationCompanyViewModel,
            qualificationViewModel = qualificationViewModel,
            studentViewModel = studentViewModel,
            paddingValues = paddingValues,
            userId = userId
        )
    }
}