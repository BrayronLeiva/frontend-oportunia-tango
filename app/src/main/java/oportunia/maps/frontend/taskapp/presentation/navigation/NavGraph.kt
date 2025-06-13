package oportunia.maps.frontend.taskapp.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import oportunia.maps.frontend.taskapp.presentation.ui.screens.AddInternshipScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.CompanyMapScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.CompanyProfileScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.CompanyRatingsScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipListCompanyScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipListStudentScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.InternshipSearch
import oportunia.maps.frontend.taskapp.presentation.ui.screens.LocationCompanyDetailScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.MainRegister
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RateCompanyScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RateStudentScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RegisterCompanyScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RegisterStudentFinal
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RegisterStudentFirst
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RegisterStudentSecond
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RegisterStudentThird
import oportunia.maps.frontend.taskapp.presentation.ui.screens.RequestDetailScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.StudentMapScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.StudentProfileScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.StudentRatingsScreen
import oportunia.maps.frontend.taskapp.presentation.ui.screens.StudentSearchScreen
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RatingCompanyStudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RegisterViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel

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
//STUDENT
@Composable
fun NavGraph(
    navController: NavHostController,
    locationCompanyViewModel: LocationCompanyViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    internshipViewModel: InternshipViewModel,
    requestViewModel: RequestViewModel,
    paddingValues: PaddingValues,
    ratingCompanyStudentViewModel: RatingCompanyStudentViewModel,
    onLogOut: () -> Unit
) {
    NavHost(navController = navController, startDestination = NavRoutes.StudentMap.ROUTE) {


        composable(NavRoutes.StudentMap.ROUTE) {
            StudentMapScreen(navController, locationCompanyViewModel, paddingValues)
        }


        composable(NavRoutes.StudentProfile.ROUTE) {
            StudentProfileScreen(
                studentViewModel,
                navController,
                onLogOut
            )
        }

        composable(NavRoutes.RequestList.ROUTE) {
            StudentProfileScreen(
                studentViewModel,
                navController,
                onLogOut
            )
        }

        composable(NavRoutes.InternshipsSearch.ROUTE) {
            InternshipSearch(internshipLocationViewModel, requestViewModel, paddingValues)
        }


        composable(NavRoutes.RequestDetail.ROUTE) {
            RequestDetailScreen(requestViewModel, paddingValues, {selectedRequest -> })
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

        //To the screen of internships list - Student
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
                internshipViewModel = internshipViewModel,
                requestViewModel = requestViewModel,
                paddingValues = paddingValues
            )
        }
        composable(
            route = "${NavRoutes.StudentRatings.ROUTE}/{${NavRoutes.StudentRatings.ARG_STUDENT_ID}}",
            arguments = listOf(navArgument(NavRoutes.StudentRatings.ARG_STUDENT_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getLong(NavRoutes.StudentRatings.ARG_STUDENT_ID) ?: 0L
            StudentRatingsScreen(
                navController = navController,
                studentId = studentId,
                ratingViewModel = ratingCompanyStudentViewModel,
                paddingValues = paddingValues
            )
        }

        composable(
            route = NavRoutes.RateCompany.ROUTE,
            arguments = listOf(navArgument(NavRoutes.RateCompany.ARG_COMPANY_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val companyId = backStackEntry.arguments?.getLong(NavRoutes.RateCompany.ARG_COMPANY_ID) ?: 0L

            RateCompanyScreen(
                navController = navController,
                ratingCompanyStudentViewModel = ratingCompanyStudentViewModel,
                companyViewModel = companyViewModel,
                studentViewModel = studentViewModel,
                companyId = companyId
            )
        }
    }

}
//COMPANY
@Composable
fun NavGraph(
    navController: NavHostController,
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    studentViewModel: StudentViewModel,
    requestViewModel: RequestViewModel,
    companyViewModel: CompanyViewModel,
    ratingCompanyStudentViewModel: RatingCompanyStudentViewModel,
    paddingValues: PaddingValues,
    onLogOut: () -> Unit
) {
    NavHost(navController = navController, startDestination = NavRoutes.CompanyMap.ROUTE) {


        composable(NavRoutes.CompanyMap.ROUTE) {
            CompanyMapScreen(
                navController = navController,
                locationCompanyViewModel = locationCompanyViewModel,
                companyViewModel = companyViewModel,
                paddingValues = paddingValues,
                onLogOut
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


        composable(NavRoutes.StudentsSearch.ROUTE) {
            StudentSearchScreen(navController, studentViewModel, requestViewModel, paddingValues, { selectStudent -> })
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
            /*
            InternshipListStudentScreen(
                locationCompanyId = locationCompanyId,
                navController = navController,
                locationCompanyViewModel = locationCompanyViewModel,
                internshipLocationViewModel = internshipLocationViewModel,
                paddingValues = paddingValues
            )

             */
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

        composable(NavRoutes.AddInternshipScreen.ROUTE) {
            AddInternshipScreen(
                navController = navController,
                internshipLocationViewModel = internshipLocationViewModel,
                locationCompanyViewModel = locationCompanyViewModel
            )
        }
        composable(NavRoutes.CompanyProfile.ROUTE) {
            CompanyProfileScreen (
                companyViewModel,
                navController,
                onLogOut
            )
        }

        composable(
            route = "${NavRoutes.CompanyRatings.ROUTE}/{${NavRoutes.CompanyRatings.ARG_COMPANY_ID}}",
            arguments = listOf(navArgument(NavRoutes.CompanyRatings.ARG_COMPANY_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val companyId = backStackEntry.arguments?.getLong(NavRoutes.CompanyRatings.ARG_COMPANY_ID) ?: 0L
            CompanyRatingsScreen(
                navController = navController,
                companyId = companyId,
                ratingViewModel = ratingCompanyStudentViewModel,
                paddingValues = paddingValues
            )
        }

        composable(
            route = NavRoutes.RateStudent.ROUTE,
            arguments = listOf(navArgument(NavRoutes.RateStudent.ARG_STUDENT_ID) {
                type = NavType.LongType
            })
        ) { backStackEntry ->
            val studentId = backStackEntry.arguments?.getLong(NavRoutes.RateStudent.ARG_STUDENT_ID) ?: 0L

            RateStudentScreen(
                navController = navController,
                ratingCompanyStudentViewModel = ratingCompanyStudentViewModel,
                companyViewModel = companyViewModel,
                studentViewModel = studentViewModel,
                studentId = studentId
            )
        }
    }
}
//REGISTER
@Composable
fun NavGraph(
    navController: NavHostController,
    userViewModel: UserViewModel,
    userRoleViewModel: UserRoleViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel,
    qualificationViewModel: QualificationViewModel,
    registerViewModel: RegisterViewModel,
    paddingValues: PaddingValues,
    onRegisterSuccess: () -> Unit
) {
    NavHost(navController = navController, startDestination = NavRoutes.MainRegister.ROUTE) {

        composable(NavRoutes.MainRegister.ROUTE) {
            MainRegister(navController, userViewModel, studentViewModel, paddingValues)
        }

        composable(NavRoutes.RegisterStudentFirst.ROUTE) {
            RegisterStudentFirst(navController, userViewModel, studentViewModel,paddingValues)
        }

        composable(NavRoutes.RegisterStudentSecond.ROUTE) {
            RegisterStudentSecond(navController, qualificationViewModel, userViewModel, userRoleViewModel, studentViewModel, paddingValues)
        }

        composable(NavRoutes.RegisterStudentThird.ROUTE) {
            RegisterStudentThird(navController, qualificationViewModel, userViewModel, userRoleViewModel, studentViewModel, registerViewModel, paddingValues)
        }

        composable(NavRoutes.RegisterStudentFinal.ROUTE) {
            RegisterStudentFinal(registerViewModel, paddingValues, onRegisterSuccess)
        }

        composable(NavRoutes.RegisterCompany.ROUTE) {
            RegisterCompanyScreen(userRoleViewModel, userViewModel, registerViewModel, onRegisterSuccess)
        }
    }

}
