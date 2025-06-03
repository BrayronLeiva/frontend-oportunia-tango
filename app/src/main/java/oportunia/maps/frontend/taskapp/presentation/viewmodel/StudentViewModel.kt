package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.domain.model.Student
import oportunia.maps.frontend.taskapp.domain.model.User
import oportunia.maps.frontend.taskapp.domain.model.UserRole
import oportunia.maps.frontend.taskapp.domain.repository.StudentRepository
import javax.inject.Inject

/**
 * Sealed class representing the various states of a task operation.
 */
sealed class StudentState {
    /** Indicates an ongoing task operation */
    data object Loading : StudentState()

    /** Contains the successfully retrieved task */
    data class Success(val student: Student) : StudentState()

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

    private val _student = MutableStateFlow<StudentState>(StudentState.Empty)
    val student: StateFlow<StudentState> = _student

    private val _selectedStudent = MutableStateFlow<Student?>(null)
    val selectedStudent: StateFlow<Student?> = _selectedStudent

    private val _studentList = MutableStateFlow<List<Student>>(emptyList())
    val studentList: StateFlow<List<Student>> = _studentList

    private val _registeredStudent = MutableStateFlow<Student?>(null)
    val registeredStudent: StateFlow<Student?> = _registeredStudent

    private val _studentDraft = MutableStateFlow(
        Student(
            id = 0L,
            name = "",
            identification = 0,
            personalInfo = "",
            experience = "",
            rating = 0.0,
            user = User(
                0L, "", "",
                lastName = "",
                enabled = false,
                tokenExpired = false,
                createDate = "",
                roles = emptyList(),
                password = ""
            )
        )
    )
    val studentDraft: StateFlow<Student> = _studentDraft

    fun selectStudentById(studentId: Long) {
        viewModelScope.launch {
            repository.findStudentById(studentId)
                .onSuccess { student ->
                    _selectedStudent.value = student
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "User $studentId")
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

    fun saveStudent() {
        viewModelScope.launch {
            val student = _studentDraft.value
            repository.saveStudent(student)
                .onSuccess { savedStudent ->
                    _registeredStudent.value = savedStudent
                    _student.value = StudentState.Success(savedStudent)
                    cleanStudentDraft()
                    Log.e("StudentViewModel", "Saved succesfully student: ${savedStudent.id}")
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error saving student" + exception.message)
                }
        }
    }


    private fun cleanStudentDraft(){
        _studentDraft.value = Student(
            id = 0L,
            name = "",
            identification = 0,
            personalInfo = "",
            experience = "",
            rating = 0.0,
            user = User(
                0L, "", "",
                lastName = "",
                enabled = false,
                tokenExpired = false,
                createDate = "",
                roles = emptyList(),
                password = ""
            )
        )
    }

    fun updateName(name: String) {
        _studentDraft.value = _studentDraft.value.copy(name = name)
    }

    fun updateIdentification(id: Int) {
        _studentDraft.value = _studentDraft.value.copy(identification = id)
    }

    fun updatePersonalInfo(info: String) {
        _studentDraft.value = _studentDraft.value.copy(personalInfo = info)
    }

    fun updateExperience(exp: String) {
        _studentDraft.value = _studentDraft.value.copy(experience = exp)
    }

    fun updateRating(rating: Double) {
        _studentDraft.value = _studentDraft.value.copy(rating = rating)
    }

    fun updateUser(email: String, password: String) {
        val updatedUser = _studentDraft.value.user.copy(email = email, password = password)
        _studentDraft.value = _studentDraft.value.copy(user = updatedUser)
    }

}