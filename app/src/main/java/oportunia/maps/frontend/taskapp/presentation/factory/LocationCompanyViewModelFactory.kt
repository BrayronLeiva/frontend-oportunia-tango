package oportunia.maps.frontend.taskapp.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import oportunia.maps.frontend.taskapp.presentation.viewmodel.LocationCompanyViewModel

/**
 * Factory for creating TaskViewModel instances with the required dependencies.
 * Provides the repository dependency to the ViewModel through the Factory pattern.
 *
 * @param locationCompanyRepository The repository that provides data access methods for tasks
 */
class LocationCompanyViewModelFactory(
    private val locationCompanyRepository: LocationCompanyRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LocationCompanyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LocationCompanyViewModel(locationCompanyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}