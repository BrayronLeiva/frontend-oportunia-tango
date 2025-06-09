package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.QualificationService
import oportunia.maps.frontend.taskapp.data.remote.api.RequestService
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestUpdateDto
import retrofit2.Response
import javax.inject.Inject


class RequestRemoteDataSource @Inject constructor(
    private val requestService: RequestService
) {
    /**
     * Retrieves all location companies from the remote API.
     *
     * @return [Result] containing a list of [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getAll(): Result<List<RequestDto>> = safeApiCall {
        requestService.getAllRequests()
    }

    /**
     * Retrieves a specific location company by its ID.
     *
     * @param id The unique identifier of the location company
     * @return [Result] containing the [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getById(id: Long): Result<RequestDto> = safeApiCall {
        requestService.getRequestById(id)
    }

    /**
     * Creates a new location company.
     *
     * @param dto The location company data to create
     * @return [Result] containing the created [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun create(dto: RequestCreateDto): Result<RequestDto> = safeApiCall {
        requestService.createRequest(dto)
    }

    /**
     * Updates an existing location company.
     *
     * @param id The unique identifier of the location company to update
     * @param dto The updated location company data
     * @return [Result] containing the updated [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun update(dto: RequestUpdateDto): Result<RequestDto> = safeApiCall {
        requestService.updateRequest(dto)
    }

    /**
     * Deletes a location company by its ID.
     *
     * @param id The unique identifier of the location company to delete
     * @return [Result] with success or failure
     */
    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        requestService.deleteRequest(id)
    }

    suspend fun deleteRequestByInternshipLocationId(internshipLocationId: Long): Result<Unit> = safeApiCall {
        requestService.deleteRequestByInternshipLocationId(internshipLocationId)
    }
    /**
     * Retrieves all location companies from the remote API.
     *
     * @return [Result] containing a list of [QualificationDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getRequestsByStudentAndCompany(studentId: Long): Result<List<RequestDto>> = safeApiCall {
        requestService.getRequestsByStudentAndCompany(studentId)
    }

    /**
     * Helper function to handle API calls safely.
     *
     * @param apiCall The suspending function making the API call
     * @return [Result] containing the data if successful, or an exception if failed
     */
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