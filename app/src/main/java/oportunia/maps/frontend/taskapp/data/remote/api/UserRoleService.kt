package oportunia.maps.frontend.taskapp.data.remote.api

import oportunia.maps.frontend.taskapp.data.remote.dto.LocationCompanyDto
import oportunia.maps.frontend.taskapp.data.remote.dto.StudentDto
import oportunia.maps.frontend.taskapp.data.remote.dto.UserRoleDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserRoleService {

    /**
     * Retrieves all userRoles entries from the remote API.
     *
     * @return [Response] containing a list of [UserRoleDto] objects if successful
     */
    @GET("userRole")
    suspend fun getAlluserRoles(): Response<List<UserRoleDto>>

    /**
     * Retrieves a specific userRole by its unique identifier.
     *
     * @param id The unique identifier of the location to retrieve
     * @return [Response] containing the requested [UserRoleDto] if successful
     */
    @GET("userRole/{id}")
    suspend fun getUserRoleById(@Path("id") id: Long): Response<UserRoleDto>

    /**
     * Creates a new userRole entry in the remote API.
     *
     * @param userRole The [UserRoleDto] object containing the data to create
     * @return [Response] containing the created [UserRoleDto] with server-assigned ID if successful
     */
    @POST("userRole")
    suspend fun createUserRole(@Body userRole: UserRoleDto): Response<UserRoleDto>

    /**
     * Updates an existing user-role entry in the remote API.
     *
     * @param id The unique identifier of the location to update
     * @param userRole The [UserRoleDto] object containing the updated data
     * @return [Response] containing the updated [UserRoleDto] if successful
     */
    @PUT("userRole/{id}")
    suspend fun updateUserRole(
        @Path("id") id: Long,
        @Body userRole: UserRoleDto
    ): Response<UserRoleDto>

    /**
     * Deletes a userRole entry from the remote API.
     *
     * @param id The unique identifier of the location to delete
     * @return [Response] indicating the success of the operation
     */
    @DELETE("userRole/{id}")
    suspend fun deleteUserRole(@Path("id") id: Long): Response<Unit>
}