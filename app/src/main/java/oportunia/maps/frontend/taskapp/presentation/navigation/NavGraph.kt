package oportunia.maps.frontend.taskapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import oportunia.maps.frontend.taskapp.presentation.ui.screens.CompanyMapScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.HomeScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipListCompanyScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipListStudentScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.LocationCompanyDetailScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.StudentMapScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.TaskDetailScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.TaskListScreen
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel

/**
 * Sets up the navigation graph for the application.
 *
 * This composable function configures the navigation structure between different screens in the app.
 * It defines two main routes:
 * - "taskList": Shows a list of all tasks
 * - "taskDetail/{taskId}": Shows details of a specific task based on the taskId
 *
 * @param navController The NavHostController used for navigation between screens.
 *                     Controls the navigation flow and back stack behavior.
 * @param taskViewModel The ViewModel instance used to observe and manipulate task data across screens.
 * @param paddingValues Provides padding values to account for UI elements like the app bar or bottom navigation,
 *                     ensuring content doesn't get hidden behind system UI elements.
 */
@Composable
fun NavGraph(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = NavRoutes.TaskList.ROUTE) {
        // TaskList screen - Entry point of the application
        // Displays all available tasks in a scrollable list with options to select individual tasks
        composable(NavRoutes.TaskList.ROUTE) {
            TaskListScreen(navController, taskViewModel, paddingValues)
        }

        // TaskDetail screen - Shows detailed information about a specific task
        // Retrieves the task based on the taskId parameter from the navigation route
        // Handles potential error cases with a default value of 0L
        composable(
            route = NavRoutes.TaskDetail.ROUTE,
            arguments = listOf(navArgument(NavRoutes.TaskDetail.ARG_TASK_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getLong(NavRoutes.TaskDetail.ARG_TASK_ID) ?: 0L
            TaskDetailScreen(
                taskId = taskId,
                taskViewModel = taskViewModel,
                navController = navController,
                paddingValues = paddingValues
            )
        }
    }
}

@Composable
fun NavGraph(
    navController: NavHostController,
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = NavRoutes.Home.ROUTE) {
        composable(NavRoutes.Home.ROUTE) {
            HomeScreen(navController, paddingValues)
        }
        composable(NavRoutes.StudentMap.ROUTE) {
            StudentMapScreen(navController, paddingValues)
        }
        composable(NavRoutes.CompanyMap.ROUTE) {
            CompanyMapScreen(navController, paddingValues)
        }

        composable(
            route = NavRoutes.LocationCompanyDetail.ROUTE,
            arguments = listOf(navArgument(NavRoutes.LocationCompanyDetail.ARG_LOCATION_COMPANY_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val locationCompanyId =
                backStackEntry.arguments?.getLong(NavRoutes.LocationCompanyDetail.ARG_LOCATION_COMPANY_ID)
                    ?: 0L
            LocationCompanyDetailScreen(
                locationCompanyId = locationCompanyId,
                locationCompanyViewModel = locationCompanyViewModel,
                navController = navController,
                paddingValues = paddingValues
            )
        }

        composable(
            route = NavRoutes.InternshipListStudent.ROUTE,
            arguments = listOf(navArgument(NavRoutes.InternshipListStudent.ARG_LOCATION_COMPANY_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val locationCompanyId =
                backStackEntry.arguments?.getLong(NavRoutes.InternshipListStudent.ARG_LOCATION_COMPANY_ID)
                    ?: 0L
            InternshipListStudentScreen(
                locationCompanyId = locationCompanyId,
                navController = navController,
                locationCompanyViewModel = locationCompanyViewModel,
                internshipLocationViewModel = internshipLocationViewModel,
                paddingValues = paddingValues
            )
        }

        composable(
            route = NavRoutes.InternshipListCompany.ROUTE,
            arguments = listOf(navArgument(NavRoutes.InternshipListCompany.ARG_LOCATION_COMPANY_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val locationCompanyId =
                backStackEntry.arguments?.getLong(NavRoutes.InternshipListCompany.ARG_LOCATION_COMPANY_ID)
                    ?: 0L
            InternshipListCompanyScreen(
                locationCompanyId = locationCompanyId,
                navController = navController,
                locationCompanyViewModel = locationCompanyViewModel,
                internshipLocationViewModel = internshipLocationViewModel,
                paddingValues = paddingValues
            )
        }
    }
}
