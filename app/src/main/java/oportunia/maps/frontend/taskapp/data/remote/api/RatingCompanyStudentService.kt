package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.RatingCompanyStudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RatingCompanyStudentRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RatingCompanyStudentService {

    /**
     * Retrieves all rating-company-student entries from the remote API.
     *
     * @return [Response] containing a list of [RatingCompanyStudentDto] objects if successful
     */
    @GET("/v1/rating-company-students")
    suspend fun getAllRatingCompanyStudents(): Response<List<RatingCompanyStudentDto>>

    /**
     * Retrieves a specific rating-company-student entry by its unique identifier.
     *
     * @param id The unique identifier of the entry to retrieve
     * @return [Response] containing the requested [RatingCompanyStudentDto] if successful
     */
    @GET("/v1/rating-company-students/{id}")
    suspend fun getRatingCompanyStudentById(@Path("id") id: Long): Response<RatingCompanyStudentDto>

    /**
     * Creates a new rating-company-student entry in the remote API.
     *
     * @param dto The [RatingCompanyStudentDto] object containing the data to create
     * @return [Response] containing the created [RatingCompanyStudentDto] with server-assigned ID if successful
     */
    @POST("/v1/rating-company-students")
    suspend fun createRatingCompanyStudent(@Body dto: RatingCompanyStudentRequestDto): Response<RatingCompanyStudentDto>

    /**
     * Updates an existing rating-company-student entry in the remote API.
     *
     * @param id The unique identifier of the entry to update
     * @param dto The [RatingCompanyStudentDto] object containing the updated data
     * @return [Response] containing the updated [RatingCompanyStudentDto] if successful
     */
    @PUT("/v1/rating-company-students/{id}")
    suspend fun updateRatingCompanyStudent(
        @Path("id") id: Long,
        @Body dto: RatingCompanyStudentDto
    ): Response<RatingCompanyStudentDto>

    /**
     * Deletes a rating-company-student entry from the remote API.
     *
     * @param id The unique identifier of the entry to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("/v1/rating-company-students/{id}")
    suspend fun deleteRatingCompanyStudent(@Path("id") id: Long): Response<Unit>
}