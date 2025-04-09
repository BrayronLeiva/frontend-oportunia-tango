package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.model.Student
import oportunia.maps.frontend.taskapp.domain.model.Task
import oportunia.maps.frontend.taskapp.domain.repository.StudentRepository
import oportunia.maps.frontend.taskapp.domain.repository.TaskRepository
import javax.inject.Inject

/**
 * Sealed class representing the various states of a task operation.
 */
sealed class StudentState {
    /** Indicates an ongoing task operation */
    data object Loading : StudentState()

    /** Contains the successfully retrieved task */
    data class Success(val task: Task) : StudentState()

    /** Indicates no task is available */
    data object Empty : StudentState()

    /** Contains an error message if the task operation fails */
    data class Error(val message: String) : StudentState()
}

/**
 * ViewModel responsible for managing task-related UI state and business logic.
 *
 * @property repository Repository interface for task operations
 */
@HiltViewModel
class StudentViewModel @Inject constructor(
    private val repository: StudentRepository
) : ViewModel() {

    private val _task = MutableStateFlow<StudentState>(StudentState.Empty)
    val task: StateFlow<StudentState> = _task

    private val _selectedStudent = MutableStateFlow<Student?>(null)
    val selectedStudent: StateFlow<Student?> = _selectedStudent

    private val _studentList = MutableStateFlow<List<Student>>(emptyList())
    val studentList: StateFlow<List<Student>> = _studentList

    /**
     * Finds a task by its ID and updates the [selectedTask] state.
     *
     * @param taskId The ID of the task to find
     */
    fun selectStudentById(studentId: Long) {
        viewModelScope.launch {
            repository.findStudentById(studentId)
                .onSuccess { student ->
                    _selectedStudent.value = student
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error fetching task by ID: ${exception.message}")
                }
        }
    }

    /**
     * Retrieves all available tasks and updates the [studentList] state.
     * Should be called when the ViewModel is initialized or when a refresh is needed.
     */
    fun findAllStudents() {
        viewModelScope.launch {
            repository.findAllStudents()
                .onSuccess { students ->
                    Log.d("StudentViewModel", "Total students: ${students.size}")
                    _studentList.value = students
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Failed to fetch students: ${exception.message}")
                }
        }
    }
}