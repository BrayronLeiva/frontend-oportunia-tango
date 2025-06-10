package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.QualificationService
import oportunia.maps.frontend.taskapp.data.remote.api.StudentService
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentRecommendedDto
import retrofit2.Response
import javax.inject.Inject

/**
 * Remote data source for student company operations.
 * Handles all network operations related to student using [studentService].
 *
 * @property studentService The service interface for student company API calls
 */
class StudentRemoteDataSource @Inject constructor(
    private val studentService: StudentService
) {
    /**
     * Retrieves all student from the remote API.
     *
     * @return [Result] containing a list of [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getAll(): Result<List<StudentDto>> = safeApiCall {
        studentService.getAllstudents()
    }

    /**
     * Retrieves a specific student company by its ID.
     *
     * @param id The unique identifier of the location company
     * @return [Result] containing the [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getById(id: Long): Result<StudentDto> = safeApiCall {
        studentService.getStudentById(id)
    }

    /**
     * Creates a new student.
     *
     * @param dto The student data to create
     * @return [Result] containing the created [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun create(dto: StudentDto): Result<StudentDto> = safeApiCall {
        studentService.createStudent(dto)
    }


    /**
     * Creates a new student.
     *
     * @param dto The student data to create
     * @return [Result] containing the created [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun create(dto: StudentCreateDto): Result<StudentDto> = safeApiCall {
        studentService.createStudent(dto)
    }


    /**
     * Updates an existing location company.
     *
     * @param id The unique identifier of the student to update
     * @param dto The updated location company data
     * @return [Result] containing the updated [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun update(id: Long, dto: StudentDto): Result<StudentDto> = safeApiCall {
        studentService.updateStudent(id, dto)
    }

    /**
     * Deletes a student by its ID.
     *
     * @param id The unique identifier of the student to delete
     * @return [Result] with success or failure
     */
    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        studentService.deleteStudent(id)
    }

    /**
     * Helper function to handle API calls safely.
     *
     * @param apiCall The suspending function making the API call
     * @return [Result] containing the data if successful, or an exception if failed
     */

    /**
     * Retrieves a specific student company by its ID.
     *
     * @param id The unique identifier of the location company
     * @return [Result] containing the [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getByLoggedStudent(): Result<StudentDto> = safeApiCall {
        studentService.getStudentByLoggedStudent()
    }


    /**
     * Retrieves all student from the remote API.
     *
     * @return [Result] containing a list of [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getRecommendedStudents(): Result<List<StudentRecommendedDto>> = safeApiCall {
        studentService.getRecommendedStudents()
    }


    /**
     * Retrieves all student from the remote API.
     *
     * @return [Result] containing a list of [StudentDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getStudentsRequestingMyCompany(): Result<List<StudentDto>> = safeApiCall {
        studentService.getStudentsRequestingMyCompany()
    }


    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.success(it)
            } ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}