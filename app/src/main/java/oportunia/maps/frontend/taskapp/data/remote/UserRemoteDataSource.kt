package oportunia.maps.frontend.taskapp.data.remote

import oportunia.maps.frontend.taskapp.data.remote.api.UserService
import oportunia.maps.frontend.taskapp.data.remote.dto.UserDto
import retrofit2.Response
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userService: UserService
) {
    suspend fun getAll(): Result<List<UserDto>> = safeApiCall {
        userService.getAllUsers()
    }

    suspend fun getUserById(id: Long): Result<UserDto> = safeApiCall {
        userService.getUserById(id)
    }

    suspend fun create(dto: UserDto): Result<UserDto> = safeApiCall {
        userService.createUser(dto)
    }

    suspend fun update(id: Long, dto: UserDto): Result<UserDto> = safeApiCall {
        userService.updateUser(id, dto)
    }

    suspend fun delete(id: Long): Result<Unit> = safeApiCall {
        userService.deleteUser(id)
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