package oportunia.maps.frontend.taskapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import oportunia.maps.frontend.taskapp.data.datasource.task.TaskDataSourceImpl
import oportunia.maps.frontend.taskapp.data.mapper.PriorityMapper
import oportunia.maps.frontend.taskapp.data.mapper.StatusMapper
import oportunia.maps.frontend.taskapp.data.mapper.TaskMapper
import oportunia.maps.frontend.taskapp.data.repository.TaskRepositoryImpl
import oportunia.maps.frontend.taskapp.presentation.factory.TaskViewModelFactory
import oportunia.maps.frontend.taskapp.presentation.navigation.NavGraph
import oportunia.maps.frontend.taskapp.presentation.ui.components.BottomNavigationRow
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import androidx.compose.ui.platform.LocalContext

@AndroidEntryPoint
class StudentActivity : ComponentActivity() {


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

    private val studentViewModel: StudentViewModel by viewModels()

    private val qualificationViewModel: QualificationViewModel by viewModels()

    private val locationCompanyViewModel: LocationCompanyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getIntExtra("userId", -1).toLong()  // -1 es el valor por defecto

        Log.d("MainActivity", "User ID recibido: $userId")
        setContent {
            TaskAppTheme {
                MainStudentScreen(
                    taskViewModel,
                    qualificationViewModel,
                    locationCompanyViewModel,
                    studentViewModel,
                    userId
                )
            }
        }
    }
}

@Composable
fun MainStudentScreen(
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
    val context = LocalContext.current
    val activity = context as? Activity
    val onLogOut: () -> Unit = {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        activity?.finish()
    }


    Scaffold(
        bottomBar = {
            BottomNavigationRow(navController = navController)
        }
    ) { paddingValues ->
        NavGraph(
            navController = navController,
            locationCompanyViewModel = locationCompanyViewModel,
            studentViewModel = studentViewModel,
            paddingValues = paddingValues,
            userId = userId,
            onLogOut = onLogOut
        )
    }

}