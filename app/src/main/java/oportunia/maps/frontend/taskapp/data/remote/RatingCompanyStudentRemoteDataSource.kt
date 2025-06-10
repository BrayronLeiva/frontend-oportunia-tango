package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.RatingCompanyStudentService
import oportunia.maps.frontend.taskapp.data.remote.dto.RatingCompanyStudentDto
import retrofit2.Response
import javax.inject.Inject

class RatingCompanyStudentRemoteDataSource @Inject constructor(
    private val ratingCompanyStudentService: RatingCompanyStudentService
) {

    /**
     * Retrieves all rating entries from the remote API.
     */
    suspend fun getAll(): Result<List<RatingCompanyStudentDto>> = safeApiCall {
        ratingCompanyStudentService.getAllRatingCompanyStudents()
    }

    /**
     * Retrieves a specific rating entry by its ID.
     */
    suspend fun getById(id: Long): Result<RatingCompanyStudentDto> = safeApiCall {
        ratingCompanyStudentService.getRatingCompanyStudentById(id)
    }

    /**
     * Creates a new rating entry.
     */
    suspend fun create(dto: RatingCompanyStudentDto): Result<RatingCompanyStudentDto> = safeApiCall {
        ratingCompanyStudentService.createRatingCompanyStudent(dto)
    }

    /**
     * Updates an existing rating entry.
     */
    suspend fun update(id: Long, dto: RatingCompanyStudentDto): Result<RatingCompanyStudentDto> = safeApiCall {
        ratingCompanyStudentService.updateRatingCompanyStudent(id, dto)
    }

    /**
     * Deletes a rating entry by its ID.
     */
    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        ratingCompanyStudentService.deleteRatingCompanyStudent(id)
    }

    /**
     * Helper function to handle API calls safely.
     */
    private suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Result<T> = try {
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { Result.success(it) }
                ?: Result.failure(Exception("Response body was null"))
        } else {
            val errorBody = response.errorBody()?.string()
            Result.failure(Exception("API error ${response.code()}: $errorBody"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}