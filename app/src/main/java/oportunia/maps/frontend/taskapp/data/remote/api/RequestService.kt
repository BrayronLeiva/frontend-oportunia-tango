package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.QualificationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestCreateDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.RequestUpdateDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RequestService {
    /**
     * Retrieves all qualifications entries from the remote API.
     *
     * @return [Response] containing a list of [LocationCompanyDto] objects if successful
     */
    @GET("/v1/requests")
    suspend fun getAllRequests(): Response<List<RequestDto>>

    /**
     * Retrieves a specific location-company by its unique identifier.
     *
     * @param id The unique identifier of the location to retrieve
     * @return [Response] containing the requested [LocationCompanyDto] if successful
     */
    @GET("/v1/requests/{id}")
    suspend fun getRequestById(@Path("id") id: Long): Response<RequestDto>

    /**
     * Creates a new location-company entry in the remote API.
     *
     * @param locationCompany The [LocationCompanyDto] object containing the data to create
     * @return [Response] containing the created [LocationCompanyDto] with server-assigned ID if successful
     */
    @POST("/v1/requests")
    suspend fun createRequest(@Body request: RequestCreateDto): Response<RequestDto>

    /**
     * Updates an existing location-company entry in the remote API.
     *
     * @param id The unique identifier of the location to update
     * @param locationCompany The [LocationCompanyDto] object containing the updated data
     * @return [Response] containing the updated [LocationCompanyDto] if successful
     */
    @PUT("/v1/requests")
    suspend fun updateRequest(
        @Body request: RequestUpdateDto
    ): Response<RequestDto>

    /**
     * Deletes a location-company entry from the remote API.
     *
     * @param id The unique identifier of the location to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("/v1/requests/{id}")
    suspend fun deleteRequest(@Path("id") id: Long): Response<Unit>
}