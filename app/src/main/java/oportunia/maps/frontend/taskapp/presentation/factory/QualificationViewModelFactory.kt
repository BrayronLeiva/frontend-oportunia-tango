package oportunia.maps.frontend.taskapp.presentation.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import oportunia.maps.frontend.taskapp.domain.repository.QualificationRepository
import oportunia.maps.frontend.taskapp.presentation.viewmodel.QualificationViewModel
import oportunia.maps.frontend.taskapp.presentation.viewmodel.TaskViewModel

/**
 * Factory for creating TaskViewModel instances with the required dependencies.
 * Provides the repository dependency to the ViewModel through the Factory pattern.
 *
 * @param qualificationRepository The repository that provides data access methods for qualifications
 */
class QualificationViewModelFactory(
    private val qualificationRepository: QualificationRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QualificationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QualificationViewModel(qualificationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}