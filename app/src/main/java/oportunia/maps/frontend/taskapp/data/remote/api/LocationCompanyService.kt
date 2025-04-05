package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import retrofit2.Response
import retrofit2.http.*

/**
 * Retrofit service interface that defines the API endpoints for LocationCompany operations.
 * This service interacts with the remote API using HTTP methods.
 */
interface LocationCompanyService {

    /**
     * Retrieves all location-company entries from the remote API.
     *
     * @return [Response] containing a list of [LocationCompanyDto] objects if successful
     */
    @GET("locationCompany")
    suspend fun getAllLocations(): Response<List<LocationCompanyDto>>

    /**
     * Retrieves a specific location-company by its unique identifier.
     *
     * @param id The unique identifier of the location to retrieve
     * @return [Response] containing the requested [LocationCompanyDto] if successful
     */
    @GET("locationCompany/{id}")
    suspend fun getLocationById(@Path("id") id: Long): Response<LocationCompanyDto>

    /**
     * Creates a new location-company entry in the remote API.
     *
     * @param locationCompany The [LocationCompanyDto] object containing the data to create
     * @return [Response] containing the created [LocationCompanyDto] with server-assigned ID if successful
     */
    @POST("locationCompany")
    suspend fun createLocation(@Body locationCompany: LocationCompanyDto): Response<LocationCompanyDto>

    /**
     * Updates an existing location-company entry in the remote API.
     *
     * @param id The unique identifier of the location to update
     * @param locationCompany The [LocationCompanyDto] object containing the updated data
     * @return [Response] containing the updated [LocationCompanyDto] if successful
     */
    @PUT("locationCompany/{id}")
    suspend fun updateLocation(
        @Path("id") id: Long,
        @Body locationCompany: LocationCompanyDto
    ): Response<LocationCompanyDto>

    /**
     * Deletes a location-company entry from the remote API.
     *
     * @param id The unique identifier of the location to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("locationCompany/{id}")
    suspend fun deleteLocation(@Path("id") id: Long): Response<Unit>
}