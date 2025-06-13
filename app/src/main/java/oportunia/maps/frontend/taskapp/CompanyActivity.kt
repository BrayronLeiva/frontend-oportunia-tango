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
import oportunia.maps.frontend.taskapp.presentation.navigation.NavGraph
import oportunia.maps.frontend.taskapp.presentation.ui.components.BottomNavigationBarCompany
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RatingCompanyStudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.RequestViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel

@AndroidEntryPoint
class CompanyActivity : ComponentActivity() {
    private val studentViewModel: StudentViewModel by viewModels()
    private val qualificationViewModel: QualificationViewModel by viewModels()
    private val locationCompanyViewModel: LocationCompanyViewModel by viewModels()
    private val internshipLocationViewModel: InternshipLocationViewModel by viewModels()
    private val requestViewModel: RequestViewModel by viewModels()
    private val internshipViewModel: InternshipViewModel by viewModels()
    private val companyViewModel: CompanyViewModel by viewModels()
    private val ratingCompanyStudentViewModel: RatingCompanyStudentViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val userId = intent.getIntExtra("userId", -1).toLong()  // -1 es el valor por defecto

        Log.d("MainActivity", "User ID recibido: $userId")
        setContent {
            TaskAppTheme {
                MainCompanyScreen(
                    qualificationViewModel,
                    locationCompanyViewModel,
                    internshipLocationViewModel,
                    companyViewModel,
                    internshipViewModel,
                    studentViewModel,
                    requestViewModel,
                    ratingCompanyStudentViewModel
                )
            }
        }
    }
}

@Composable
fun MainCompanyScreen(
    qualificationViewModel: QualificationViewModel,
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel,
    companyViewModel: CompanyViewModel,
    internshipViewModel: InternshipViewModel,
    studentViewModel: StudentViewModel,
    requestViewModel: RequestViewModel,
    ratingCompanyStudentViewModel: RatingCompanyStudentViewModel,
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        locationCompanyViewModel.findAllLocations()
    }

    val context = LocalContext.current
    val activity = context as? Activity
    val onLogOut: () -> Unit = {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        activity?.finish()
    }

    // Mantener el estado de la ruta actual
    var currentDestination by remember { mutableStateOf<String?>(null) }

    // Monitorear cambios en la ruta de navegación
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            currentDestination = entry.destination.route
        }
    }


    Scaffold(
        bottomBar = {
            BottomNavigationBarCompany(navController = navController)
        }
    ) { paddingValues ->

        NavGraph(
            navController = navController,
            locationCompanyViewModel = locationCompanyViewModel,
            internshipLocationViewModel = internshipLocationViewModel,
            studentViewModel = studentViewModel,
            requestViewModel = requestViewModel,
            companyViewModel = companyViewModel,
            ratingCompanyStudentViewModel = ratingCompanyStudentViewModel,
            paddingValues = paddingValues,
            onLogOut
        )
    }

}