package oportunia.maps.frontend.taskapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser
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
import oportunia.maps.frontend.taskapp.presentation.ui.screens.LoginScreen
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserViewModel


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskAppTheme {
                MainLoginScreen(
                    userViewModel
                )
            }
        }
    }
}

@Composable
fun MainLoginScreen(

    userViewModel: UserViewModel
) {
    val navController = rememberNavController()
    val loggedInUser by userViewModel.loggedInUser.collectAsState()
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


    Scaffold { paddingValues ->
        LoginScreen(
            userViewModel = userViewModel,
            onLoginSuccess = { userId ->

                loggedInUser?.let { user ->
                    when (user.roles[0].name) {
                        TypeUser.STU -> {
                            val intent = Intent(context, StudentActivity::class.java).apply {
                                putExtra("userId", userId)
                            }
                            context.startActivity(intent)
                            activity?.finish()
                        }
                        TypeUser.COM -> {
                            val intent = Intent(context, CompanyActivity::class.java).apply {
                                putExtra("userId", userId)
                            }
                            context.startActivity(intent)
                            activity?.finish()
                        }
                        else -> {
                            Toast.makeText(context, "Rol de usuario desconocido", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            onRegisterClick = { context ->
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            },
            paddingValues = paddingValues
        )
    }

    /*Scaffold () { paddingValues ->
        LoginScreen(
            userViewModel = userViewModel,
            onLoginSuccess = { userId ->

                loggedInUser?.let { userRole ->
                    when (userRole.role.name) {
                        TypeUser.STU -> {
                            val intent = Intent(context, StudentActivity::class.java).apply {
                                putExtra("userId", userId)
                            }
                            context.startActivity(intent)
                            activity?.finish()
                        }
                        TypeUser.COM -> {
                            val intent = Intent(context, CompanyActivity::class.java).apply {
                                putExtra("userId", userId)
                            }
                            context.startActivity(intent)
                            activity?.finish()
                        }
                        else -> {
                            Toast.makeText(context, "Rol de usuario desconocido", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            onRegisterClick = { context ->
                val intent = Intent(context, RegisterActivity::class.java)
                context.startActivity(intent)
                activity?.finish()
            },
            paddingValues = paddingValues
        )
    }*/

    fun navigateToStudentScreen(context: Context) {
        val intent = Intent(context, StudentActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToCompanyScreen(context: Context) {
        val intent = Intent(context, CompanyActivity::class.java)
        context.startActivity(intent)
    }


}