package oportunia.maps.frontend.taskapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
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
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.CompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {
    private val userRoleViewModel: UserRoleViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    private val studentViewModel: StudentViewModel by viewModels()
    private val qualificationViewModel: QualificationViewModel by viewModels()
    private val companyViewModel: CompanyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskAppTheme {
                MainRegisterScreen(
                    userViewModel,
                    userRoleViewModel,
                    studentViewModel,
                    companyViewModel,
                    qualificationViewModel
                )
            }
        }
    }
}

@Composable
fun MainRegisterScreen(
    userViewModel: UserViewModel,
    userRoleViewModel: UserRoleViewModel,
    studentViewModel: StudentViewModel,
    companyViewModel: CompanyViewModel,
    qualificationViewModel: QualificationViewModel
) {
    val navController = rememberNavController()




    // Mantener el estado de la ruta actual
    var currentDestination by remember { mutableStateOf<String?>(null) }

    // Monitorear cambios en la ruta de navegación
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            currentDestination = entry.destination.route
        }
    }

    // Obtenemos el contexto
    val context = LocalContext.current
    val activity = context as? Activity
    val onRegisterSuccess: () -> Unit = {
        val intent = Intent(context, LoginActivity::class.java)
        context.startActivity(intent)
        activity?.finish()
    }

    Scaffold { paddingValues ->
        NavGraph(
            navController = navController,
            userViewModel = userViewModel,
            userRoleViewModel = userRoleViewModel,
            studentViewModel = studentViewModel,
            companyViewModel = companyViewModel,
            qualificationViewModel = qualificationViewModel,
            paddingValues = paddingValues,
            onRegisterSuccess = onRegisterSuccess
        )
    }

}