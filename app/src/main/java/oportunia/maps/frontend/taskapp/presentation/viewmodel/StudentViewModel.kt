package oportunia.maps.frontend.taskapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationRequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentRecommendedDto
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


sealed class StudentListState {
    /** Indicates an ongoing task operation */
    data object Loading : StudentListState()

    /** Contains the successfully retrieved task */
    data class Success(val studentList: List<Student>) : StudentListState()

    /** Indicates no task is available */
    data object Empty : StudentListState()

    /** Contains an error message if the task operation fails */
    data class Error(val message: String) : StudentListState()
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

    private val _studentListState = MutableStateFlow<StudentListState>(StudentListState.Empty)
    val studentListState: StateFlow<StudentListState> = _studentListState

    private val _studentState = MutableStateFlow<StudentState>(StudentState.Empty)
    val studentState: StateFlow<StudentState> = _studentState

    private val _selectedStudent = MutableStateFlow<Student?>(null)
    val selectedStudent: StateFlow<Student?> = _selectedStudent

    private val _studentList = MutableStateFlow<List<Student>>(emptyList())
    val studentList: StateFlow<List<Student>> = _studentList

    private val _studentRecommendedList = MutableStateFlow<List<StudentRecommendedDto>>(emptyList())
    val studentRecommendedList: StateFlow<List<StudentRecommendedDto>> = _studentRecommendedList

    private val _registeredStudent = MutableStateFlow<Student?>(null)
    val registeredStudent: StateFlow<Student?> = _registeredStudent

    private val _studentDraft = MutableStateFlow(
        Student(
            id = 0L,
            name = "",
            identification = "",
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
        _studentState.value = StudentState.Loading
        viewModelScope.launch {
            repository.findStudentById(studentId)
                .onSuccess { student ->
                    _selectedStudent.value = student
                    _studentState.value = StudentState.Success(student)
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "User $studentId")
                    Log.e("StudentViewModel", "Error fetching student by ID: ${exception.message}")
                    _studentState.value = StudentState.Error(exception.toString())
                }
        }
    }

    fun getLoggedStudent() {
        _studentState.value = StudentState.Loading
        viewModelScope.launch {
            repository.findLoggedStudent()
                .onSuccess { student ->
                    _selectedStudent.value = student
                    _studentState.value = StudentState.Success(student)
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error fetching student by ID: ${exception.message}")
                    _studentState.value = StudentState.Error(exception.toString())
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



    fun loadStudentsRecommended() {
        _studentListState.value = StudentListState.Loading
        viewModelScope.launch {
            Log.d("StudentViewModel", "Search By AI")
            //By the moment
            repository.findRecommendedStudents()
                .onSuccess { studentLists ->
                    if (studentLists.isEmpty()) {
                        _studentListState.value = StudentListState.Empty
                    } else {
                        Log.d("StudentViewModel", "Total Interships: ${studentLists.size}"
                        )
                        _studentRecommendedList.value = studentLists
                        _studentListState.value = StudentListState.Success(emptyList())
                    }
                }
                .onFailure { exception ->
                    Log.e("StudentViewModel", "Error fetching internships locations: ${exception.message}"
                    )
                    _studentListState.value = StudentListState.Error("Failed to fetch students: ${exception.message}")
                    //_internshipsLocationList.value = emptyList() // o lo que quieras mostrar en error
                }
        }
    }

    /**
     * Retrieves internships for a specific location and updates the [internships] state.
     *
     * @param locationId The ID of the location to retrieve internships for
     */
    fun loadStudentsRequestingToMyCompany() {
        viewModelScope.launch {
            _studentListState.value = StudentListState.Loading
            repository.findStudentsRequestingMyCompany()
                .onSuccess { studentList ->
                    if (studentList.isEmpty()) {
                        _studentListState.value = StudentListState.Empty
                    } else {
                        Log.d("StudentViewModel", "Total Students: ${studentList.size}")
                        _studentListState.value = StudentListState.Success(studentList)
                        _studentList.value = studentList
                    }
                }
                .onFailure { exception ->
                    _studentListState.value = StudentListState.Error("Failed to fetch students $: ${exception.message}")
                    Log.e("StudentViewModel", "Error fetching students: ${exception.message}")
                }
        }
    }












    fun saveStudent() {
        viewModelScope.launch {
            val student = _studentDraft.value
            repository.saveStudent(student)
                .onSuccess { savedStudent ->
                    _registeredStudent.value = savedStudent
                    _studentState.value = StudentState.Success(savedStudent)
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
            identification = "",
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

    fun updateIdentification(id: String) {
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