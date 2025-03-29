package oportunia.maps.frontend.taskapp.presentation.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import oportunia.maps.frontend.taskapp.domain.repository.InternshipLocationRepository
import oportunia.maps.frontend.taskapp.domain.repository.LocationCompanyRepository
import oportunia.maps.frontend.taskapp.presentation.viewmodel.InternshipLocationViewModel

/**
 * Factory for creating InternshipLocationViewModel instances with the required dependencies.
 * Provides the repository dependency to the ViewModel through the Factory pattern.
 *
 * @param internshipLocationRepository The repository that provides data access methods for internships
 */
class InternshipLocationViewModelFactory(
    private val locationCompanyRepository: LocationCompanyRepository,
    private val internshipLocationRepository: InternshipLocationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InternshipLocationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InternshipLocationViewModel(locationCompanyRepository, internshipLocationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}