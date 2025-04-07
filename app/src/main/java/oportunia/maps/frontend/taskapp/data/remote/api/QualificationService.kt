package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import retrofit2.Response
import retrofit2.http.*

interface QualificationService {

    /**
     * Retrieves all qualifications entries from the remote API.
     *
     * @return [Response] containing a list of [LocationCompanyDto] objects if successful
     */
    @GET("qualification")
    suspend fun getAllqualifications(): Response<List<QualificationDto>>

    /**
     * Retrieves a specific location-company by its unique identifier.
     *
     * @param id The unique identifier of the location to retrieve
     * @return [Response] containing the requested [LocationCompanyDto] if successful
     */
    @GET("qualification/{id}")
    suspend fun getQualificationById(@Path("id") id: Long): Response<QualificationDto>

    /**
     * Creates a new location-company entry in the remote API.
     *
     * @param locationCompany The [LocationCompanyDto] object containing the data to create
     * @return [Response] containing the created [LocationCompanyDto] with server-assigned ID if successful
     */
    @POST("qualification")
    suspend fun createQualification(@Body qualification: QualificationDto): Response<QualificationDto>

    /**
     * Updates an existing location-company entry in the remote API.
     *
     * @param id The unique identifier of the location to update
     * @param locationCompany The [LocationCompanyDto] object containing the updated data
     * @return [Response] containing the updated [LocationCompanyDto] if successful
     */
    @PUT("qualification/{id}")
    suspend fun updateQualification(
        @Path("id") id: Long,
        @Body qualification: QualificationDto
    ): Response<QualificationDto>

    /**
     * Deletes a location-company entry from the remote API.
     *
     * @param id The unique identifier of the location to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("qualification/{id}")
    suspend fun deleteLocation(@Path("id") id: Long): Response<Unit>
}