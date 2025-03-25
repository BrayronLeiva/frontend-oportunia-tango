package oportunia.maps.frontend.taskapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import oportunia.maps.frontend.taskapp.data.datasource.internshiplocation.InternshipLocationDataSourceImpl
import oportunia.maps.frontend.taskapp.data.datasource.locationcompany.LocationCompanyDataSourceImpl
import oportunia.maps.frontend.taskapp.data.mapper.CompanyMapper
import oportunia.maps.frontend.taskapp.data.mapper.InternshipLocationMapper
import oportunia.maps.frontend.taskapp.data.mapper.InternshipMapper
import oportunia.maps.frontend.taskapp.data.mapper.LocationCompanyMapper
import oportunia.maps.frontend.taskapp.data.mapper.UserMapper
import oportunia.maps.frontend.taskapp.data.repository.InternshipLocationRepositoryImpl
import oportunia.maps.frontend.taskapp.data.repository.LocationCompanyRepositoryImpl
import oportunia.maps.frontend.taskapp.presentation.factory.InternshipLocationViewModelFactory
import oportunia.maps.frontend.taskapp.presentation.factory.LocationCompanyViewModelFactory
import oportunia.maps.frontend.taskapp.presentation.navigation.NavGraph
import oportunia.maps.frontend.taskapp.presentation.ui.theme.TaskAppTheme
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel

/**
 * Main activity that serves as the entry point for the application.
 * Initializes the TaskViewModel and sets up the Compose UI with the main screen.
 */
class MapActivity : ComponentActivity() {
    private val userMapper = UserMapper()
    private val internshipMapper = InternshipMapper()
    private val companyMapper = CompanyMapper(userMapper)
    private val locationCompanyMapper = LocationCompanyMapper(companyMapper)
    private val internshipLocationMapper = InternshipLocationMapper(locationCompanyMapper, internshipMapper)

    private lateinit var locationCompanyViewModel: LocationCompanyViewModel
    private lateinit var internshipLocationViewModel: InternshipLocationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create data sources and repositories
        val locationCompanyDataSource = LocationCompanyDataSourceImpl(locationCompanyMapper)
        val locationCompanyRepository = LocationCompanyRepositoryImpl(locationCompanyDataSource, locationCompanyMapper)

        val internshipLocationDataSource = InternshipLocationDataSourceImpl(internshipLocationMapper, internshipMapper)
        val internshipLocationRepository = InternshipLocationRepositoryImpl(internshipLocationDataSource, internshipLocationMapper, internshipMapper)

        // Create ViewModel Factory
        val locationCompanyViewModelFactory = LocationCompanyViewModelFactory(locationCompanyRepository)
        val internshipLocationViewModelFactory = InternshipLocationViewModelFactory(locationCompanyRepository, internshipLocationRepository)

        // Manually initialize ViewModels with the factory
        locationCompanyViewModel = ViewModelProvider(this, locationCompanyViewModelFactory).get(LocationCompanyViewModel::class.java)
        internshipLocationViewModel = ViewModelProvider(this, internshipLocationViewModelFactory).get(InternshipLocationViewModel::class.java)

        setContent {
            TaskAppTheme {
                MainScreen(
                    locationCompanyViewModel = locationCompanyViewModel,
                    internshipLocationViewModel = internshipLocationViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    locationCompanyViewModel: LocationCompanyViewModel,
    internshipLocationViewModel: InternshipLocationViewModel
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        locationCompanyViewModel.findAllLocations()
    }

    Scaffold { paddingValues ->
        NavGraph(
            navController = navController,
            locationCompanyViewModel = locationCompanyViewModel,
            internshipLocationViewModel = internshipLocationViewModel,
            paddingValues = paddingValues
        )
    }
}