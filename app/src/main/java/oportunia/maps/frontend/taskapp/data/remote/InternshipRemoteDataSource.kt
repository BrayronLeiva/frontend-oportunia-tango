package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.InternshipLocationService
import oportunia.maps.frontend.taskapp.data.remote.api.InternshipService
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import retrofit2.Response
import javax.inject.Inject

class InternshipRemoteDataSource @Inject constructor(
    private val internshipService: InternshipService
) {
    /**
     * Retrieves all location companies from the remote API.
     *
     * @return [Result] containing a list of [LocationCompanyDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getAll(): Result<List<InternshipDto>> = safeApiCall {
        internshipService.getAllInternships()
    }

    /**
     * Retrieves a specific location company by its ID.
     *
     * @param id The unique identifier of the location company
     * @return [Result] containing the [LocationCompanyDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getById(id: Long): Result<InternshipDto> = safeApiCall {
        internshipService.getInternshipById(id)
    }

    /**
     * Creates a new location company.
     *
     * @param dto The location company data to create
     * @return [Result] containing the created [LocationCompanyDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun create(dto: InternshipDto): Result<InternshipDto> = safeApiCall {
        internshipService.createInternship(dto)
    }

    /**
     * Updates an existing location company.
     *
     * @param id The unique identifier of the location company to update
     * @param dto The updated location company data
     * @return [Result] containing the updated [LocationCompanyDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun update(id: Long, dto: InternshipDto): Result<InternshipDto> =
        safeApiCall {
            internshipService.updateInternship(id, dto)
        }

    /**
     * Deletes a location company by its ID.
     *
     * @param id The unique identifier of the location company to delete
     * @return [Result] with success or failure
     */
    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        internshipService.deleteInternship(id)
    }

    /**
     * Helper function to handle API calls safely.
     *
     * @param apiCall The suspending function making the API call
     * @return [Result] containing the data if successful, or an exception if failed
     */

    /**
     * Retrieves all location companies from the remote API.
     *
     * @return [Result] containing a list of [LocationCompanyDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getInternshipsByLocationId(locationId: Long): Result<List<InternshipDto>> = safeApiCall {
        internshipService.getInternshipsByLocation(locationId)
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