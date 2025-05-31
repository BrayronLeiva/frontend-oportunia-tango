package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface InternshipLocationService {

    /**
     * Retrieves all location-company entries from the remote API.
     *
     * @return [Response] containing a list of [LocationCompanyDto] objects if successful
     */
    @GET("internship-locations")
    suspend fun getAllInternshipsLocations(): Response<List<InternshipLocationDto>>

    /**
     * Retrieves a specific location-company by its unique identifier.
     *
     * @param id The unique identifier of the location to retrieve
     * @return [Response] containing the requested [InternshipLocationDto] if successful
     */
    @GET("internship-locations/{id}")
    suspend fun getInternshipLocationById(@Path("id") id: Long): Response<InternshipLocationDto>

    /**
     * Creates a new location-company entry in the remote API.
     *
     * @param InternshipLocation The [InternshipLocationDto] object containing the data to create
     * @return [Response] containing the created [InternshipLocationDto] with server-assigned ID if successful
     */
    @POST("internship-locations")
    suspend fun createInternshipLocation(@Body locationCompany: InternshipLocationDto): Response<InternshipLocationDto>

    /**
     * Updates an existing location-company entry in the remote API.
     *
     * @param id The unique identifier of the location to update
     * @param locationCompany The [LocationCompanyDto] object containing the updated data
     * @return [Response] containing the updated [LocationCompanyDto] if successful
     */
    @PUT("internship-locations/{id}")
    suspend fun updateInternshipLocation(
        @Path("id") id: Long,
        @Body internshipLocation: InternshipLocationDto
    ): Response<InternshipLocationDto>

    /**
     * Deletes a location-company entry from the remote API.
     *
     * @param id The unique identifier of the location to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("internship-locations/{id}")
    suspend fun deleteInternshipLocation(@Path("id") id: Long): Response<Unit>

    /**
        Comments
     */
    @GET("internship-locations-match")
    suspend fun getRecommendedInternshipsLocations(): Response<List<InternshipLocationRecommendedDto>>
}