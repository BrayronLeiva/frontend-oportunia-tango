package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.model.RatingCompanyStudent
import oportunia.maps.frontend.taskapp.domain.repository.RatingCompanyStudentRepository
import javax.inject.Inject


sealed class RatingCompanyStudentState {
    data object Loading : RatingCompanyStudentState()
    data class Success(val ratingCompanyStudentList: List<RatingCompanyStudent>) : RatingCompanyStudentState()
    data class Error(val message: String) : RatingCompanyStudentState()
    data object Empty : RatingCompanyStudentState()
}


/**
 * ViewModel responsible for managing rating-company-student related UI state and logic.
 *
 * @property repository Repository interface for rating-company-student operations
 */
@HiltViewModel
class RatingCompanyStudentViewModel @Inject constructor(
    private val repository: RatingCompanyStudentRepository
) : ViewModel() {

    private val _ratingCompanyStudentList = MutableStateFlow<List<RatingCompanyStudent>>(emptyList())
    val ratingCompanyStudentList: StateFlow<List<RatingCompanyStudent>> = _ratingCompanyStudentList

    /**
     * Retrieves all available rating-company-student entries and updates the state.
     */
    fun findAllRatingCompanyStudents() {
        viewModelScope.launch {
            repository.findAllRatingCompanyStudents()
                .onSuccess { list ->
                    Log.d("RatingCompanyStudentVM", "Fetched ${list.size} entries")
                    _ratingCompanyStudentList.value = list
                }
                .onFailure { exception ->
                    Log.e("RatingCompanyStudentVM", "Failed to fetch entries: ${exception.message}")
                }
        }
    }
}