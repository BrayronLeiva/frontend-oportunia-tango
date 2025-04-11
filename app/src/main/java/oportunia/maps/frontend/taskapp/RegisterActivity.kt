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
import oportunia.maps.frontend.taskapp.presentation.ui.components.BottomNavigationRow
import oportunia.maps.frontend.taskapp.presentation.ui.screens.LoginScreen
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.StudentViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel

@AndroidEntryPoint
class RegisterActivity : ComponentActivity() {

    private val userRoleViewModel: UserRoleViewModel by viewModels()

    private val studentViewModel: StudentViewModel by viewModels()

    private val qualificationViewModel: QualificationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskAppTheme {
                MainRegisterScreen(
                    userRoleViewModel,
                    studentViewModel,
                    qualificationViewModel
                )
            }
        }
    }
}

@Composable
fun MainRegisterScreen(

    userRoleViewModel: UserRoleViewModel,
    studentViewModel: StudentViewModel,
    qualificationViewModel: QualificationViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {

    }
    // Obtenemos el contexto
    val context = LocalContext.current
    val activity = context as? Activity

    // Mantener el estado de la ruta actual
    var currentDestination by remember { mutableStateOf<String?>(null) }

    // Monitorear cambios en la ruta de navegación
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { entry ->
            currentDestination = entry.destination.route
        }
    }

    val onRegisterSuccess: (Int) -> Unit = { userId ->
        val intent = Intent(context, StudentActivity::class.java).apply {
            putExtra("userId", userId)
        }
        context.startActivity(intent)
        activity?.finish()
    }

    Scaffold { paddingValues ->
        NavGraph(
            navController = navController,
            userRoleViewModel = userRoleViewModel,
            studentViewModel = studentViewModel,
            qualificationViewModel = qualificationViewModel,
            paddingValues = paddingValues,
            onRegisterSuccess = onRegisterSuccess
        )
    }

}