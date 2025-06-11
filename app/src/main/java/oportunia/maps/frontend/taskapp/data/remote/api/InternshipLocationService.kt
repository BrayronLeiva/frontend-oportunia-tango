package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationFlagDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRecommendedFlagDto
import oportunia.maps.frontend.taskapp.data.remote.dto.InternshipLocationRequestDto
import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface InternshipLocationService {

    /**
     * Retrieves all location-company entries from the remote API.
     *
     * @return [Response] containing a list of [LocationCompanyDto] objects if successful
     */
    @GET("/v1/internship-locations")
    suspend fun getAllInternshipsLocations(): Response<List<InternshipLocationDto>>


    /**
     * Retrieves all location-company entries from the remote API.
     *
     * @return [Response] containing a list of [LocationCompanyDto] objects if successful
     */
    @GET("/v1/internship-locations/available")
    suspend fun getAllInternshipsLocationsAvailable(): Response<List<InternshipLocationDto>>

    /**
     * Retrieves a specific location-company by its unique identifier.
     *
     * @param id The unique identifier of the location to retrieve
     * @return [Response] containing the requested [InternshipLocationDto] if successful
     */
    @GET("/v1/internship-locations/{id}")
    suspend fun getInternshipLocationById(@Path("id") id: Long): Response<InternshipLocationDto>

    /**
     * Creates a new location-company entry in the remote API.
     *
     * @param InternshipLocation The [InternshipLocationDto] object containing the data to create
     * @return [Response] containing the created [InternshipLocationDto] with server-assigned ID if successful
     */
    @POST("/v1/internship-locations")
    suspend fun createInternshipLocation(@Body dto: InternshipLocationRequestDto): Response<InternshipLocationDto>

    /**
     * Updates an existing location-company entry in the remote API.
     *
     * @param id The unique identifier of the location to update
     * @param locationCompany The [LocationCompanyDto] object containing the updated data
     * @return [Response] containing the updated [LocationCompanyDto] if successful
     */
    @PUT("/v1/internship-locations/{id}")
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
    @DELETE("/v1/internship-locations/{id}")
    suspend fun deleteInternshipLocation(@Path("id") id: Long): Response<Unit>

    @GET("/v1/internship-locations/location/{locationId}")
    suspend fun getInternshipsByLocationId(@Path("locationId") locationId: Long): Response<List<InternshipLocationDto>>

    @GET("/v1/internship-locations/location/flag-byStudent/{locationId}")
    suspend fun getInternshipsLocationsFlagByLocationId(@Path("locationId") locationId: Long): Response<List<InternshipLocationFlagDto>>

    @GET("/v1/internship-locations/flag/student")
    suspend fun getInternshipsLocationsFlag(): Response<List<InternshipLocationFlagDto>>

    /**
        Comments
     */
    @GET("/v1/internship-locations/recommendations")
    suspend fun getRecommendedInternshipsLocations(): Response<List<InternshipLocationRecommendedDto>>

    /**
    Comments
     */
    @GET("/v1/internship-locations/available/recommendations")
    suspend fun getRecommendedInternshipsLocationsAvailable(): Response<List<InternshipLocationRecommendedDto>>
    /**
    Comments
     */

    @GET("/v1/internship-locations/recommendations/flag")
    suspend fun getRecommendedInternshipsLocationsFlag(): Response<List<InternshipLocationRecommendedFlagDto>>

}