package oportunia.maps.frontend.taskapp

import android.app.Activity
import android.content.Context
import dagger.hilt.android.AndroidEntryPoint
import oportunia.maps.frontend.taskapp.presentation.viewmodel.UserRoleViewModel
import android.os.Bundle
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
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel

import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import oportunia.maps.frontend.taskapp.data.remote.dto.enumClasses.TypeUser


@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val userRoleViewModel: UserRoleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TaskAppTheme {
                MainLoginScreen(
                    userRoleViewModel
                )
            }
        }
    }
}

@Composable
fun MainLoginScreen(

    userRoleViewModel: UserRoleViewModel
) {
    val navController = rememberNavController()
    val loggedInUser by userRoleViewModel.loggedInUser.collectAsState()
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


    Scaffold () { paddingValues ->
        LoginScreen(
            userRoleViewModel = userRoleViewModel,
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
    }

    fun navigateToStudentScreen(context: Context) {
        val intent = Intent(context, StudentActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToCompanyScreen(context: Context) {
        val intent = Intent(context, CompanyActivity::class.java)
        context.startActivity(intent)
    }


}