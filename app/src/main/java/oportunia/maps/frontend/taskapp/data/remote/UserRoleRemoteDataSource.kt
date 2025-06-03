package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.UserRoleService
import oportunia.maps.frontend.taskapp.data.remote.dto.UserRoleDto
import retrofit2.Response
import javax.inject.Inject


/**
 * Remote data source for userRole operations.
 * Handles all network operations related to userRoles using [userRoleService].
 *
 * @property userRoleService The service interface for userRole API calls
 */
class UserRoleRemoteDataSource @Inject constructor(
    private val userRoleService: UserRoleService
) {
    /**
     * Retrieves all location companies from the remote API.
     *
     * @return [Result] containing a list of [UserRoleDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getAll(): Result<List<UserRoleDto>> = safeApiCall {
        userRoleService.getAlluserRoles()
    }

    /**
     * Retrieves a specific userRole by its ID.
     *
     * @param id The unique identifier of the location company
     * @return [Result] containing the [UserRoleDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun getById(id: Long): Result<UserRoleDto> = safeApiCall {
        userRoleService.getUserRoleById(id)
    }

    /**
     * Creates a new UserRole.
     *
     * @param dto The UserRole data to create
     * @return [Result] containing the created [UserRoleDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun create(dto: UserRoleDto): Result<UserRoleDto> = safeApiCall {
        userRoleService.createUserRole(dto)
    }

    /**
     * Updates an existing UserRole.
     *
     * @param id The unique identifier of the UserRole to update
     * @param dto The updated location company data
     * @return [Result] containing the updated [UserRoleDto] if successful,
     * or an exception if the operation failed
     */
    suspend fun update(id: Long, dto: UserRoleDto): Result<UserRoleDto> = safeApiCall {
        userRoleService.updateUserRole(id, dto)
    }

    /**
     * Deletes a UserRole by its ID.
     *
     * @param id The unique identifier of the UserRole to delete
     * @return [Result] with success or failure
     */
    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        userRoleService.deleteUserRole(id)
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
