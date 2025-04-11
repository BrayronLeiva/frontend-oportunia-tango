package oportunia.maps.frontend.taskapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import oportunia.maps.frontend.taskapp.data.datasource.userrole.UserRoleProvider
import oportunia.maps.frontend.taskapp.domain.model.Student
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.presentation.screens.StudentProfileScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.MainRegister
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RegisterStudentFirst
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RegisterStudentSecond
import oportunia.maps.frontend.taskapp.presentation.ui.screens.CompanyMapScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.HomeScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipListCompanyScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipListStudentScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipSearch
import oportunia.maps.frontend.taskapp.presentation.ui.screens.LocationCompanyDetailScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.LoginScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.StudentMapScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.TaskDetailScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.TaskListScreen
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel

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

/*@Composable
fun NavGraph(
    navController: NavHostController,
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    qualificationViewModel: QualificationViewModel,
    paddingValues: PaddingValues
) {
    NavHost(navController = navController, startDestination = NavRoutes.Login.ROUTE) {

        composable(NavRoutes.MainRegister.ROUTE) {
            MainRegister(navController, paddingValues)
        }

        composable(NavRoutes.RegisterStudentFirst.ROUTE) {
            RegisterStudentFirst(navController, paddingValues)
        }

        composable(NavRoutes.RegisterStudentSecond.ROUTE) {
            RegisterStudentSecond(navController, qualificationViewModel, paddingValues)
        }

        composable(NavRoutes.Home.ROUTE) {
            HomeScreen(navController, paddingValues)
        }
        composable(NavRoutes.StudentMap.ROUTE) {
            StudentMapScreen(navController, paddingValues)
        }
        composable(NavRoutes.CompanyMap.ROUTE) {
            CompanyMapScreen(navController, paddingValues)
        }
        composable(NavRoutes.InternshipsSearch.ROUTE) {
            InternshipSearch(internshipLocationViewModel, paddingValues)
        }

        composable(NavRoutes.StudentProfile.ROUTE) {
            StudentProfileScreen(navController, Student(1, "Rodney", 402640339, "I love Android Studio", "Kotlin Developer", 4.5, User(3, "rodney@est.una.ac.cr", "123")) ) //Estudiante temporal
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

        composable(NavRoutes.Login.ROUTE) {
            LoginScreen(navController, paddingValues)

        }

    }*/
    @Composable
    fun NavGraph(
        navController: NavHostController,
        locationCompanyViewModel: LocationCompanyViewModel,
        qualificationViewModel: QualificationViewModel,
        studentViewModel: StudentViewModel,
        paddingValues: PaddingValues,
        userId: Long
    ) {
        NavHost(navController = navController, startDestination = NavRoutes.Home.ROUTE) {

            composable(NavRoutes.MainRegister.ROUTE) {
                MainRegister(navController, paddingValues)
            }

            composable(NavRoutes.RegisterStudentFirst.ROUTE) {
                RegisterStudentFirst(navController, paddingValues)
            }

            composable(NavRoutes.RegisterStudentSecond.ROUTE) {
                RegisterStudentSecond(navController, qualificationViewModel, paddingValues)
            }

            composable(NavRoutes.Home.ROUTE) {
                HomeScreen(navController, paddingValues)
            }
            composable(NavRoutes.StudentMap.ROUTE) {
                StudentMapScreen(navController, locationCompanyViewModel, paddingValues)
            }
            composable(NavRoutes.CompanyMap.ROUTE) {
                CompanyMapScreen(navController, paddingValues)
            }
            //composable(NavRoutes.InternshipsSearch.ROUTE) {
            //    InternshipSearch(internshipLocationViewModel, paddingValues)
            //}

            composable(NavRoutes.StudentProfile.ROUTE) {
                StudentProfileScreen(
                    navController,
                    studentViewModel,
                    userId
                )
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
                /*InternshipListStudentScreen(
                    locationCompanyId = locationCompanyId,
                    navController = navController,
                    locationCompanyViewModel = locationCompanyViewModel,
                    internshipLocationViewModel = internshipLocationViewModel,
                    paddingValues = paddingValues
                )*/
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
                /*InternshipListCompanyScreen(
                    locationCompanyId = locationCompanyId,
                    navController = navController,
                    locationCompanyViewModel = locationCompanyViewModel,
                    internshipLocationViewModel = internshipLocationViewModel,
                    paddingValues = paddingValues
                )*/
            }


        }

}
